package com.mubaracktahir.news.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mubaracktahir.news.core.adapters.HorizontalAdapter.MyViewHolder.Companion.from
import com.mubaracktahir.news.data.db.entity.Article
import com.mubaracktahir.news.databinding.HorizontalNewRecyclerItemBinding

/**
 * Created by Mubarak Tahir on 6/19/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */

class HorizontalAdapter :
    RecyclerView.Adapter<HorizontalAdapter.MyViewHolder>() {
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

        return from(parent)
    }


    fun setOnclickListener(listener: OnclickListener?) {
        this.listener = listener
    }

    interface OnclickListener {
        fun onItemClicked(note: Article, position: Int)
    }

    override fun getItemCount() = articles.size
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note: Article = articles.get(position)
        holder.bindView(note)
    }

    class MyViewHolder private constructor(val binding: HorizontalNewRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(article: Article) {
            binding.article = article

            binding.date.text = article.publishedAt
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HorizontalNewRecyclerItemBinding.inflate(
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


}



