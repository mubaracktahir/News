package com.mubaracktahir.news.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mubaracktahir.news.data.db.entity.NewsObject
import com.mubaracktahir.news.utils.exceptions.NoInternetEception
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

class NewsDataSourceImpl(val newsApiService: NewsApiService) : NewsDataSource {
    private val _retrievedNewsObject =  MutableLiveData<NewsObject>()
    override val retrievedNewsObject: LiveData<NewsObject>
        get() =_retrievedNewsObject


    override suspend fun fetchCurrentNews(
        source: String,
        sortBy: String
    ) {

        try {
            val fetchCurrentNews = newsApiService
                .getNews(source)
                .await()
            _retrievedNewsObject.postValue(fetchCurrentNews)
        } catch (e: NoInternetEception) {
            Timber.i("No,Internet Connection")
        }
        catch ( d : SocketTimeoutException){
            Timber.d("Weak or no internet connection")
        }

    }
}