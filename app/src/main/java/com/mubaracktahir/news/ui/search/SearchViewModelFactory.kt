package com.mubaracktahir.news.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mubaracktahir.news.data.repo.NewsRepository


/**
 * Created by Mubarak Tahir on 6/15/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */

class SearchViewModelFactory(private val newsRepository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(newsRepository) as T
    }
}