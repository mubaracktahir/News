package com.mubaracktahir.news.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mubaracktahir.news.data.repo.NewsRepository


/**
 * Created by Mubarak Tahir on 6/15/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */

class HomeViewModelFactory(private val newsRepository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(newsRepository) as T
    }
}