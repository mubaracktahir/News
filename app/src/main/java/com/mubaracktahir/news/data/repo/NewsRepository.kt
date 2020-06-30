package com.mubaracktahir.news.data.repo

import androidx.lifecycle.LiveData
import com.mubaracktahir.news.data.db.entity.Article
import com.mubaracktahir.news.data.db.entity.NewsObject


/**
 * Created by Mubarak Tahir on 6/14/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */
interface NewsRepository {
    suspend fun searchNews(query: String):NewsObject
    suspend fun getNews(): LiveData<NewsObject>
}