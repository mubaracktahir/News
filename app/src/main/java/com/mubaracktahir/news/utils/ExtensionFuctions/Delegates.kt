package com.mubaracktahir.news.utils.ExtensionFuctions

import kotlinx.coroutines.*


/**
 * Created by Mubarak Tahir on 6/15/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T) : Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async (start = CoroutineStart.LAZY){
            block.invoke(this)
        }
    }
}