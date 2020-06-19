package com.mubaracktahir.news.ui.home

import androidx.lifecycle.ViewModel
import com.mubaracktahir.news.data.network.ConnectivityInterceptorImpl
import com.mubaracktahir.news.data.network.NewsApiService
import com.mubaracktahir.news.data.network.NewsDataSourceImpl
import com.mubaracktahir.news.data.repo.NewsRepository
import com.mubaracktahir.news.utils.ExtensionFuctions.lazyDeferred

class HomeViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val news by lazyDeferred {
        newsRepository.reload()
    }

    val newsObjet by lazyDeferred {
        newsRepository.getNews()
    }



}
