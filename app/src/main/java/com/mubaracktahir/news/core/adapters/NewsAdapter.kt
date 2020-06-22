package com.mubaracktahir.news.core.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mubaracktahir.news.R
import com.mubaracktahir.news.data.db.entity.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_recycler_item.view.*


/**
 * Created by Mubarak Tahir on 6/19/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */
class NewsAdapter(val layout: Int, val articles : List<Article>) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>()
     {
    private var listener: OnclickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val note: Article = articles.get(position)
        holder.bindView(note)
    }


    fun setOnclickListener(listener: OnclickListener?) {
        this.listener = listener
    }

    interface OnclickListener {
        fun onItemClicked(note: Article,position: Int)
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bindView(article: Article) {
            Picasso.get()
                .load(article.urlToImage)
                .placeholder(R.drawable.background)
                .into(itemView.newsThumbnailImageView)
            itemView.newsTitleTextView.text = article.title
            itemView.newsDetailTextView.text = article.description
            itemView.date.setText("BBC NEWS ")
            itemView.date.append( Html.fromHtml("&#8226"))
            itemView.date.append(" 1 hour ago")

        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null) {
                    listener!!.onItemClicked(articles.get(position),position)
                }
            }
        }
    }
    companion object {
        private val diffCallback: DiffUtil.ItemCallback<Article> =
            object : DiffUtil.ItemCallback<Article>() {
                override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(
                    oldItem: Article,
                    newItem: Article
                ): Boolean {
                    return oldItem.title == newItem.title
                            && oldItem.description == newItem.description
                            && oldItem.author == newItem.author
                            && oldItem.publishedAt == newItem.publishedAt
                            && oldItem.urlToImage == newItem.urlToImage
                            && oldItem.url == newItem.url

                }
            }
    }

         override fun getItemCount() = articles.size
     }
