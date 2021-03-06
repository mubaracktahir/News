package com.mubaracktahir.news.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mubaracktahir.news.data.db.entity.Article
import com.mubaracktahir.news.data.db.entity.NewsObject
import com.mubaracktahir.news.utils.Constants.NewsUrls.API_KEY
import com.mubaracktahir.news.utils.Constants.NewsUrls.API_KEY_QUERY
import com.mubaracktahir.news.utils.Constants.NewsUrls.BASE_URL
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*


/**
 * Created by Mubarak Tahir on 6/13/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */

interface NewsApiService {
    @GET("top-headlines")
    fun getGlobalNewsAsync(
        @Query("sources") source: String
    ): Deferred<NewsObject>


    @GET("top-headlines")
    fun getTopNewsAsync(
        @Query("country") country: String = Locale.getDefault().toString(),
        @Query("category") cat: String
    ): Deferred<NewsObject>


    @GET("everything")
    fun searchNewsAsync(
        @Query("q") searchQuery: String,
        @Query("sortBy") sortBy: String = "pubishedAt"
    ): Deferred<NewsObject>

    @GET("top-headlines")
    fun getLocaleNewsAsync(
        @Query("country") country: String
    ): Deferred<NewsObject>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): NewsApiService {
            val interceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter(API_KEY_QUERY, API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApiService::class.java);
        }
    }

}