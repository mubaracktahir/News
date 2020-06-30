package com.mubaracktahir.news.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mubaracktahir.news.data.db.entity.NewsObject
import com.mubaracktahir.news.data.repo.NewsRepository
import com.mubaracktahir.news.utils.ExtensionFuctions.lazyDeferred
import com.mubaracktahir.news.utils.exceptions.NoInternetEception
import kotlinx.coroutines.*
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

class SearchViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    private val _searchResult = MutableLiveData<NewsObject>()
    val searchResult: LiveData<NewsObject> get() = _searchResult

    fun searchNews(query: String) {
        coroutineScope.launch {
            try {
                val searches = newsRepository.searchNews(query) as NewsObject
                _searchResult.value = searches
            } catch (e: NoInternetEception) {
                Timber.i("No,Internet Connection")
            } catch (d: SocketTimeoutException) {
                Timber.d("Weak or no internet connection")
            } catch (i: SSLHandshakeException) {
                Timber.d("Weak or no internet connection")
            } catch (d: ConnectException) {
                Timber.d("Weak or no internet connection")
            }

        }

    }

}
