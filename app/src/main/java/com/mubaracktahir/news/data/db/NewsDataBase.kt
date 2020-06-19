package com.mubaracktahir.news.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mubaracktahir.news.data.db.daos.NewsDao
import com.mubaracktahir.news.data.db.entity.NewsObject
import com.mubaracktahir.news.data.db.typeconverters.Converter
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Mubarak Tahir on 6/14/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */

@Database(
    entities = [NewsObject::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class NewsDataBase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    companion object {
        @Volatile private var instance: NewsDataBase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabse(context).also { instance = it }
        }
        private fun buildDatabse(context: Context) =
            Room.databaseBuilder(context.applicationContext, NewsDataBase::class.java, "news.db")
                .build()
    }
}