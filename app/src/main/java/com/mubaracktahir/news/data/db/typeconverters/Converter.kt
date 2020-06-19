package com.mubaracktahir.news.data.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mubaracktahir.news.data.db.entity.Article


/**
 * Created by Mubarak Tahir on 6/18/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */
class Converter {

    @TypeConverter
    fun listToJson(value: List<Article>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Article>::class.java).toList()


}