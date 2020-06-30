package com.mubaracktahir.news.data.network

import androidx.lifecycle.LiveData
import com.mubaracktahir.news.data.db.entity.Article
import com.mubaracktahir.news.data.db.entity.NewsObject


/**
 * Created by Mubarak Tahir on 6/14/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */
interface NewsDataSource {
    val retrievedLocaleNews:LiveData<NewsObject>
    val retrievedGlobalNews: LiveData<NewsObject>
    val retrievedTrendingObject: LiveData<NewsObject>

    val retrievedSearchObject: LiveData<NewsObject>

    suspend fun fetchLocaleNews(
        country: String
    )
    suspend fun fetchGlobalNews(
        source: String
    )

    suspend fun fetchTrending(
        country: String = "ng",
        category: String
    )

    suspend fun fetchSearchedNews(
        searchQuery: String
    ):NewsObject
}