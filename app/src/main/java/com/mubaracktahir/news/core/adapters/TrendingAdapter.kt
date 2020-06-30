package com.mubaracktahir.news.core.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mubaracktahir.news.R
import com.mubaracktahir.news.data.db.entity.Article
import kotlinx.android.synthetic.main.story_recycler_item.view.*


/**
 * Created by Mubarak Tahir on 6/19/2020.
 * Mubby inc
 * mubarack.tahirr@gmail.com
 */

enum class Category(name: String) {
    BUSINESS("business"), SPORT("sport"), ENTERTAINMENT("entertainment"),
    EDUCATION("education"), SCIENCE("science"), RELIGION("religion")
}

class TrendingAdapter(val layout: Int) :
    RecyclerView.Adapter<TrendingAdapter.MyViewHolder>() {
    private var listener: OnclickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MyViewHolder(v)
    }


    fun setOnclickListener(listener: OnclickListener?) {
        this.listener = listener
    }

    interface OnclickListener {
        fun onItemClicked(category: Category)
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bindView(position: Int) {
            itemView.profile_image.setImageResource(image.get(position))
        }

        init {
            itemView.setOnClickListener {
                if (listener != null) {

                }
            }
        }
    }

    val list = arrayListOf<String>(
        "Business",
        "Sport",
        "Entertainment",
        "Education",
        "Science",
        "Religion"
    )
    val image = arrayListOf<Int>(
        R.drawable.business,
        R.drawable.sport,
        R.drawable.entertaiment,
        R.drawable.education,
        R.drawable.science,
        R.drawable.religion
    )

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

    override fun onBindViewHolder(holder: TrendingAdapter.MyViewHolder, position: Int) {
        holder.itemView.trendingTitle.text = list[position]
        holder.bindView(position)
    }

    override fun getItemCount() = list.size
}
