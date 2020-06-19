package com.mubaracktahir.news.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mubaracktahir.news.data.db.entity.ID
import com.mubaracktahir.news.data.db.entity.NewsObject


/**
 * Created by Mubarak Tahir on 6/14/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upDate(newsObject: NewsObject)

    @Query("Select * from news_object where id = $ID")
    fun getNews(): LiveData<NewsObject>

}