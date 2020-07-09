package com.mubaracktahir.news.data.repo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mubaracktahir.news.data.db.daos.NewsDao
import com.mubaracktahir.news.data.db.entity.NewsObject
import com.mubaracktahir.news.data.network.NewsDataSourceImpl
import kotlinx.coroutines.*
import okhttp3.internal.wait
import java.time.ZonedDateTime

class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val newsDataSource: NewsDataSourceImpl
) : NewsRepository {

    val _newsObject = MutableLiveData<NewsObject>()
    val newsObject: LiveData<NewsObject>
        get() = _newsObject

    init {
        newsDataSource.retrievedLocaleNews.observeForever {

        }
        newsDataSource.retrievedTrendingObject.observeForever {

        }
        newsDataSource.retrievedSearchObject.observeForever {
            _newsObject.postValue(it)
        }
        newsDataSource.retrievedGlobalNews.observeForever {
            saveNews(it)
        }
    }

    override suspend fun getNews(): LiveData<NewsObject> {
        return withContext(Dispatchers.IO) {
            initNewsData()
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

    private suspend fun fetchCurrentNews() {
        newsDataSource.fetchGlobalNews("bbc-news")
    }

    override suspend fun searchNews(query: String): NewsObject {
        return newsDataSource.fetchSearchedNews(query)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isFetchCurrentNewsNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val fifteenMunitesAgo = ZonedDateTime.now().minusMinutes(15)

        return lastFetchTime.isBefore(fifteenMunitesAgo)


    }

}