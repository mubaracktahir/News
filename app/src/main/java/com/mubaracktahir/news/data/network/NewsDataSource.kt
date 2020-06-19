package com.mubaracktahir.news.data.network

import androidx.lifecycle.LiveData
import com.mubaracktahir.news.data.db.entity.NewsObject


/**
 * Created by Mubarak Tahir on 6/14/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */
interface NewsDataSource {
    val retrievedNewsObject: LiveData<NewsObject>
    suspend fun fetchCurrentNews(
        source: String,
        sortBy: String
    )
}