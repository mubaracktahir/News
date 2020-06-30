package com.mubaracktahir.news.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mubaracktahir.news.data.db.entity.Article
import com.mubaracktahir.news.data.db.entity.NewsObject
import com.mubaracktahir.news.data.repo.NewsRepository
import com.mubaracktahir.news.utils.ExtensionFuctions.lazyDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


enum class NewsApiStatus {
    LOADING, ERROR, DONE
}

class HomeViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus>
        get() = _status

    val newsObject by lazyDeferred {
        newsRepository.getNews()
    }

}
