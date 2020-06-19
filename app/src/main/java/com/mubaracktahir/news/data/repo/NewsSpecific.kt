package com.mubaracktahir.news.data.repo

import com.mubaracktahir.news.data.db.entity.Article


/**
 * Created by Mubarak Tahir on 6/14/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */
interface NewsSpecific {
    val articles: List<Article>
    val sortBy: String
    val source: String
    val status: String
}