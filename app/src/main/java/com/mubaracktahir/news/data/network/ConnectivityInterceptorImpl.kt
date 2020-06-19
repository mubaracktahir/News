package com.mubaracktahir.news.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.mubaracktahir.news.utils.exceptions.NoInternetEception
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {
    private val appContextb = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {

        if(!isOnline())
            throw NoInternetEception()
            return chain.proceed(chain.request())
    }

    fun isOnline(): Boolean {
        val connectivityManager =
            appContextb.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val newtworkInfo = connectivityManager.activeNetworkInfo
        return newtworkInfo != null && newtworkInfo.isConnected
    }
}