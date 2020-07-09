package com.mubaracktahir.news.core

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mubaracktahir.news.R
import com.mubaracktahir.news.data.db.entity.Article
import com.mubaracktahir.news.ui.home.NewsApiStatus
import com.squareup.picasso.Picasso


/**
 * Created by Mubarak Tahir on 6/24/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */



@BindingAdapter("articleDescription")
fun TextView.setDescription(article: Article?) {
    article?.let {
        text = article.description
    }
}

@BindingAdapter("articleTitle")
fun TextView.setTitle(article: Article?) {
    article?.let {
        text = article.title
    }
}

@BindingAdapter("articleImage")
fun ImageView.setImage(article: Article?) {
    article?.let {
        Picasso.get()
            .load(article.urlToImage)
            .placeholder(R.drawable.background)
            .into(this)
    }
}

@BindingAdapter("newsApiStatus")
fun setImage(view: ImageView, status: NewsApiStatus?) {
    status?.let {
        when (status) {
            NewsApiStatus.LOADING -> {
                view.visibility = View.VISIBLE
                view.setImageResource(R.drawable.loading_animator)
            }
            NewsApiStatus.ERROR -> {
                view.visibility = View.VISIBLE
                view.setImageResource(R.drawable.heart)
            }
            NewsApiStatus.DONE -> view.visibility = View.GONE


        }
    }
}

@BindingAdapter("articleTime")
fun setTime(view: TextView, article: Article?) {
    article?.let {
        view.text = article.publishedAt
    }
}


@BindingAdapter("newsApiStatus")
fun updateprogress(progressBar: ProgressBar, status: NewsApiStatus?) {
    status?.let {
        when (it) {
            NewsApiStatus.DONE -> progressBar.visibility = View.GONE
            NewsApiStatus.LOADING -> progressBar.visibility = View.VISIBLE
            NewsApiStatus.ERROR -> progressBar.visibility = View.GONE
        }
    }
}