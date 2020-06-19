package com.mubaracktahir.news.data.repo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.mubaracktahir.news.data.db.daos.NewsDao
import com.mubaracktahir.news.data.db.entity.NewsObject
import com.mubaracktahir.news.data.network.NewsDataSource
import com.mubaracktahir.news.data.network.NewsDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val newsDataSource: NewsDataSourceImpl
) : NewsRepository {

    init {
        newsDataSource.retrievedNewsObject.observeForever {
            saveNews(it)
        }
    }

    override suspend fun getNews(): LiveData<NewsObject> {
        return withContext(Dispatchers.IO) {
            initNewsData()
            return@withContext newsDao.getNews()
        }
    }

    override suspend fun reload(): LiveData<NewsObject> {
        return withContext(Dispatchers.IO) {
            fetchCurrentNews()
            return@withContext newsDao.getNews()
        }
    }

    private fun saveNews(newsObject: NewsObject) {
        GlobalScope.launch(Dispatchers.IO) {
            newsDao.upDate(newsObject)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun initNewsData() {
        if (isFetchCurrentNewsNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentNews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun reloadNews() {
            fetchCurrentNews()
    }

    private suspend fun fetchCurrentNews() {
        newsDataSource.fetchCurrentNews("bbc-news", "top")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNewsNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val fifteenMunitesAgo = ZonedDateTime.now().minusMinutes(15)

        return lastFetchTime.isBefore(fifteenMunitesAgo)
    }
}