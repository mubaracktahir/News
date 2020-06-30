package com.mubaracktahir.news.ui.search

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mubaracktahir.news.R
import com.mubaracktahir.news.core.adapters.NewsAdapter
import com.mubaracktahir.news.data.db.entity.Article
import com.mubaracktahir.news.data.db.entity.NewsObject
import com.mubaracktahir.news.databinding.SearchFragmentBinding
import com.mubaracktahir.news.ui.base.BaseFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SearchFragment : BaseFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: SearchViewModelFactory by instance()
    private lateinit var binding: SearchFragmentBinding
    private lateinit var viewModel: SearchViewModel
    lateinit var adapter3: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)

        binding.ser.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.searchNews(binding.textViewToolbarTitle.text.toString())
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        buildUi()
    }


    private fun buildUi() = launch {

        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = View.GONE
            setUpHeadLinesAdapter(it)
        })

    }

    fun setUpHeadLinesAdapter(it: NewsObject) {
        adapter3 = NewsAdapter(NewsAdapter.ArticleListener { article, position ->
            var b =
                context?.applicationContext?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            b.vibrate(30)
        })
        adapter3.articles = it.articles
        binding.newsRecycler.adapter = adapter3
        binding.newsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.newsRecycler.setHasFixedSize(true)
        adapter3.setOnclickListener(object : NewsAdapter.OnclickListener {
            override fun onItemClicked(note: Article, position: Int) {

            }
        })

    }

}
