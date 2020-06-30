package com.mubaracktahir.news.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException


/**
 * Created by Mubarak Tahir on 6/20/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */
class EqualImageView : AppCompatImageView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context!!, attrs, defStyleAttr) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}

class MyWork ( appContext: Context,  params : WorkerParameters) : CoroutineWorker(appContext,params){
    /*override val kodein by closestKodein()
    val repo : NewsRepositoryImpl by instance()*/
    override suspend fun doWork(): Result {

      /*  val repo = NewsRepositoryImpl(N, NewsDataSourceImpl(
            NewsApiService.invoke(ConnectivityInterceptorImpl(appContext))))*/

        return try {
           // repo.getNews()
            Result.success()
        } catch (ex : HttpException){
            Result.retry()
        }
    }



}
