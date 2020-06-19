package com.mubaracktahir.news

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mubaracktahir.news.data.db.NewsDataBase
import com.mubaracktahir.news.data.network.*
import com.mubaracktahir.news.data.repo.NewsRepository
import com.mubaracktahir.news.data.repo.NewsRepositoryImpl
import com.mubaracktahir.news.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import timber.log.Timber


/**
 * Created by Mubarak Tahir on 6/11/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */
class News : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidModule(this@News))
        bind() from singleton { NewsDataBase(instance()) }
        bind() from singleton { instance<NewsDataBase>().newsDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { NewsApiService(instance()) }
        bind<NewsDataSource>() with singleton { NewsDataSourceImpl(instance()) }
        bind<NewsDataSourceImpl>() with  singleton { NewsDataSourceImpl(instance()) }
        bind<NewsRepository>() with singleton { NewsRepositoryImpl(instance(), instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidThreeTen.init(this@News)
    }
}