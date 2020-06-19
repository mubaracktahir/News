package com.mubaracktahir.news.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mubaracktahir.news.R
import com.mubaracktahir.news.databinding.HomeFragmentBinding
import com.mubaracktahir.news.ui.base.BaseFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class HomeFragment : BaseFragment(), KodeinAware {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    override val kodein by closestKodein()
    private val viewModelFactory: HomeViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        buildUI()
    }

    private fun buildUI() = launch {
        val latestNews = viewModel.newsObjet.await()
        latestNews.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it == null) return@Observer
           // binding.doss.text = it.articles.get(0).urlToImage
            //binding.progressCircular.visibility = View.GONE
        })
    }
}
