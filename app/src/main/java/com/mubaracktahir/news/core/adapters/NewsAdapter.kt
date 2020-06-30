package com.mubaracktahir.news.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mubaracktahir.news.data.db.entity.Article
import com.mubaracktahir.news.databinding.NewsRecyclerItemBinding


/**
 * Created by Mubarak Tahir on 6/19/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */
class NewsAdapter(val cliclListener: ArticleListener) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    var articles = listOf<Article>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var listener: OnclickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val note: Article = articles.get(position)
        holder.bind(note, position,cliclListener)
    }

    fun setOnclickListener(listener: OnclickListener?) {
        this.listener = listener
    }

    interface OnclickListener {
        fun onItemClicked(note: Article, position: Int)
    }

    class MyViewHolder private constructor(val binding: NewsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            article: Article,
            pos: Int,
            cliclListener: ArticleListener
        ) {
            binding.article = article
            binding.clickListener = cliclListener
            binding.executePendingBindings()
            if (pos < 10)
                binding.root.setBackgroundColor(article.getColor(pos))
            else
                binding.root.setBackgroundColor(article.getColor(pos - 10))


        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsRecyclerItemBinding.inflate(
                    layoutInflater, parent,
                    false
                )
                return MyViewHolder(binding)
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
                    return oldItem == newItem
                }
            }
    }

    override fun getItemCount() = articles.size

    class ArticleListener(val cliclListener: (article: Article, position : Int) -> Unit) {
        fun onClick(article: Article) = cliclListener(article,0)
    }
}
