package com.mubaracktahir.news.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mubaracktahir.news.data.db.entity.NewsObject
import com.mubaracktahir.news.utils.exceptions.NoInternetEception
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

class NewsDataSourceImpl(val newsApiService: NewsApiService) : NewsDataSource {

    private val _retrievedLocaleNews = MutableLiveData<NewsObject>()
    override val retrievedLocaleNews: LiveData<NewsObject>
        get() = _retrievedLocaleNews

    private val _retrievedGlobalNews = MutableLiveData<NewsObject>()
    override val retrievedGlobalNews: LiveData<NewsObject>
        get() = _retrievedGlobalNews

    private val _retrievedTrendingObject = MutableLiveData<NewsObject>()
    override val retrievedTrendingObject: LiveData<NewsObject>
        get() = _retrievedTrendingObject

    private val _retrievedSearchObject = MutableLiveData<NewsObject>()
    override val retrievedSearchObject: LiveData<NewsObject>
        get() = _retrievedSearchObject

    /**
     *
     * sets the value of the [_retrievedLocaleNews] to the NewsObject retrieved from the web
     * @param country retrieves news based on the value passed
     *
     */
    override suspend fun fetchLocaleNews(country: String) {
        val fetchLocaleNews = newsApiService
            .getLocaleNewsAsync(country)
            .await()
        _retrievedLocaleNews.postValue(fetchLocaleNews)
    }

    /**
     * set the value of [_retrievedGlobalNews] to the NewsObject retrieved From the web
     * @param source retrieves news based on the source passed
     */
    override suspend fun fetchGlobalNews(
        source: String
    ) {

        try {
            val fetchCurrentNews = newsApiService
                .getGlobalNewsAsync(source).await()
            _retrievedGlobalNews.postValue(fetchCurrentNews)
        } catch (e: NoInternetEception) {
            Timber.i("No,Internet Connection")
        } catch (d: SocketTimeoutException) {
            Timber.d("Weak or no internet connection")
        } catch (i: SSLHandshakeException) {

        } catch (d: ConnectException) {
            Timber.d("Weak or no internet connection")
        }
    }

    override suspend fun fetchTrending(country: String, category: String) {
        val fetchTopNews = newsApiService
            .getTopNewsAsync(country, category)
            .await()
        _retrievedTrendingObject.postValue(fetchTopNews)

    }

    override suspend fun fetchSearchedNews(searchQuery: String) = newsApiService
        .searchNewsAsync(searchQuery = searchQuery)
        .await()
}