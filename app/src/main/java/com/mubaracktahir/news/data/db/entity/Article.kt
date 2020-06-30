package com.mubaracktahir.news.data.db.entity


import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Article(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) {


    fun getColor(pos: Int): Int {
        return when (pos) {
            0 -> Color.parseColor("#E0FFF7")
            1 -> Color.parseColor("#E0FFF7")
            2 -> Color.parseColor("#FFFBF1")
            3 -> Color.parseColor("#FFFBF1")
            4 -> Color.parseColor("#F0F8FF")
            5 -> Color.parseColor("#F0F8FF")
            6 -> Color.parseColor("#E6EEFF")
            7 -> Color.parseColor("#E6EEFF")
            8 -> Color.parseColor("#ECECEC")
            9 -> Color.parseColor("#ECECEC")

            else -> Color.parseColor("#ECECEC")
        }
    }
}