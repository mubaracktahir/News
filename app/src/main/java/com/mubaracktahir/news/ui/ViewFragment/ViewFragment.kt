package com.mubaracktahir.news.ui.ViewFragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mubaracktahir.news.R

class ViewFragment : Fragment() {

    companion object {
        fun newInstance() = ViewFragment()
    }

    private lateinit var viewModel: ViewFragViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ViewFragViewModel::class.java)
    }

}
