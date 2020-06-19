package com.mubaracktahir.news.data.db.entity


import androidx.room.*

const val ID =0
@Entity(tableName = "news_object")
data class NewsObject(
    val articles: List<Article>,

    val sortBy: String,

    val source: String,

    val status: String

) {
    @PrimaryKey(autoGenerate = false)
    private var id = 0

    fun getId( ) = this.id
    fun setId(id: Int) {
        this.id = id
    }

}