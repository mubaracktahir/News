package com.mubaracktahir.news.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

data class NewsObject2(
    val articles: List<ArticleX>,
    val status: String,
    val totalResults: Int
) {
    private var id = 0

    fun getId() = this.id
    fun setId(id: Int) {
        this.id = id
    }
}