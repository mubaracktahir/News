package com.mubaracktahir.news.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.mubaracktahir.news.R
import com.mubaracktahir.news.core.adapters.HorizontalAdapter
import com.mubaracktahir.news.core.adapters.NewsAdapter
import com.mubaracktahir.news.core.adapters.TrendingAdapter
import com.mubaracktahir.news.data.db.entity.Article
import com.mubaracktahir.news.data.db.entity.NewsObject
import com.mubaracktahir.news.databinding.HomeFragmentBinding
import com.mubaracktahir.news.ui.base.BaseFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import timber.log.Timber


class HomeFragment : BaseFragment(), KodeinAware {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    override val kodein by closestKodein()
    private val viewModelFactory: HomeViewModelFactory by instance()
    lateinit var adapter: TrendingAdapter
    lateinit var adapter2: HorizontalAdapter
    lateinit var adapter3: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        buildUI()
        Timber.d("onCreateView")

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onActivityCreated")

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun buildUI() = launch {
        val latestNews = viewModel.newsObjet.await()
        latestNews.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it == null) return@Observer
            binding.progressCircular.visibility = View.GONE

            setUpHeadLinesAdapter(it)
            setUpTopNewsAdapter(it)
            setUpTrendingAdapter(it)


            //enable textViews
            binding.headlines.visibility = View.VISIBLE
            binding.topnews.visibility = View.VISIBLE
            binding.trending.visibility = View.VISIBLE
            Toast.makeText(context, "Unable to refresh field", Toast.LENGTH_LONG).show()
        })
    }

    fun setUpHeadLinesAdapter(it: NewsObject) {
        adapter3 = NewsAdapter(R.layout.news_recycler_item, it.articles)
        binding.newsRecycler.adapter = adapter3
        binding.newsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.newsRecycler.setHasFixedSize(true)
    }

    fun setUpTrendingAdapter(it: NewsObject) {
        adapter = TrendingAdapter(R.layout.story_recycler_item, it.articles)
        adapter.setOnclickListener(object : TrendingAdapter.OnclickListener {
            override fun onItemClicked(note: Article) {
                Toast.makeText(context, "${note.title}", Toast.LENGTH_LONG).show()
            }
        })
        binding.storyRecycler.adapter = adapter
        binding.storyRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.storyRecycler.setHasFixedSize(true)
    }


    fun setUpTopNewsAdapter(it: NewsObject) {
        adapter2 = HorizontalAdapter(R.layout.horizontal_new_recycler_item, it.articles)
        binding.horizontalNewsRecycler.adapter = adapter2
        binding.horizontalNewsRecycler.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
    }


}
