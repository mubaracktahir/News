package com.mubaracktahir.news.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class ArticleX(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)