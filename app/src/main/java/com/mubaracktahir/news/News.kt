package com.mubaracktahir.news

import android.app.Application
import android.os.Build
import androidx.work.*
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mubaracktahir.news.data.db.NewsDataBase
import com.mubaracktahir.news.data.network.*
import com.mubaracktahir.news.data.repo.NewsRepository
import com.mubaracktahir.news.data.repo.NewsRepositoryImpl
import com.mubaracktahir.news.ui.home.HomeViewModelFactory
import com.mubaracktahir.news.ui.search.SearchViewModelFactory
import com.mubaracktahir.news.utils.MyWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
 * Created by Mubarak Tahir on 6/11/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */
class News : Application(), KodeinAware {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override val kodein = Kodein.lazy {
        import(androidModule(this@News))
        bind() from singleton { NewsDataBase(instance()) }
        bind() from singleton { instance<NewsDataBase>().newsDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { NewsApiService(instance()) }
        bind() from singleton { NewsDataSourceImpl(instance()) }
        bind() from singleton { NewsRepositoryImpl(instance(), instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { SearchViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidThreeTen.init(this@News)
        delayInit()
    }

    private fun delayInit() {
        applicationScope.launch {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .setRequiresBatteryNotLow(true)
                .apply {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        setRequiresDeviceIdle(true)
                }.build()
            val repeatRequest = PeriodicWorkRequestBuilder<MyWork>(
                1,
                TimeUnit.DAYS
            )
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork(
                "referesh",
                ExistingPeriodicWorkPolicy.KEEP ,
                repeatRequest
            )
        }
    }

}