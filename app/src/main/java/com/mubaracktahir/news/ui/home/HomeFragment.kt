package com.mubaracktahir.news.ui.home

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Vibrator
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.mubaracktahir.news.R
import com.mubaracktahir.news.core.adapters.Category
import com.mubaracktahir.news.core.adapters.HorizontalAdapter
import com.mubaracktahir.news.core.adapters.NewsAdapter
import com.mubaracktahir.news.core.adapters.TrendingAdapter
import com.mubaracktahir.news.data.db.entity.NewsObject
import com.mubaracktahir.news.databinding.HomeFragmentBinding
import com.mubaracktahir.news.ui.MainActivity
import com.mubaracktahir.news.ui.base.BaseFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import timber.log.Timber
import com.mubaracktahir.news.core.adapters.NewsAdapter.ArticleListener
import com.mubaracktahir.news.data.db.entity.Article

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
        binding.setLifecycleOwner(this@HomeFragment)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onActivityCreated")

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun buildUI() = launch {
        val latestNews = viewModel.newsObject.await()
        latestNews.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it == null) return@Observer
            binding.progressCircular.visibility = View.GONE
            Timber.d(it.articles.toString())
            setUpHeadLinesAdapter(it)
            setUpTopNewsAdapter(it)
            setUpTrendingAdapter(it)
            //enable textViews
            binding.view.visibility = View.VISIBLE
            Toast.makeText(context, "Unable to refresh field", Toast.LENGTH_LONG).show()
        })
    }

    fun setUpHeadLinesAdapter(it: NewsObject) {
        adapter3 = NewsAdapter(ArticleListener{
            article, position ->
            var b = context?.applicationContext?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            var pp = arrayOf(0,1000,0)
            b.vibrate(30)
            loadUrl(article,position)
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

    fun setUpTrendingAdapter(it: NewsObject) {
        adapter = TrendingAdapter(R.layout.story_recycler_item)
        adapter.setOnclickListener(object : TrendingAdapter.OnclickListener {
            override fun onItemClicked(category: Category) {

            }
        })
        binding.storyRecycler.adapter = adapter
        binding.storyRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.storyRecycler.setHasFixedSize(true)
    }


    fun setUpTopNewsAdapter(it: NewsObject) {
        adapter2 = HorizontalAdapter()
        adapter2.articles = it.articles
        binding.horizontalNewsRecycler.adapter = adapter2
        binding.horizontalNewsRecycler.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        adapter2.setOnclickListener(object : HorizontalAdapter.OnclickListener {
            override fun onItemClicked(note: Article, position: Int) {
                loadUrl(note, position)
            }
        })

        binding.horizontalNewsRecycler.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    addDot(position, it.articles.size)

                }
            }
        )
    }
    private fun addDot(position: Int, size: Int) {
        val textViews = arrayOfNulls<TextView>(size)
        binding.liner.removeAllViews()
        var i = 0
        while (i < size) {
            textViews[i] = TextView(this?.context)
            textViews[i]?.setText(Html.fromHtml("&#8226;"))
            textViews[i]?.textSize = 35f
            textViews[i]?.setTextColor(resources.getColor(android.R.color.darker_gray))
            textViews[i]?.setOnClickListener {
                binding.horizontalNewsRecycler.setCurrentItem(i)
                Toast.makeText(this.context, "ckloo $i", Toast.LENGTH_LONG).show()
            }
            binding.liner.addView(textViews[i])
            i++

        }
        if (textViews.size > 0)
            textViews[position]?.setTextColor(resources.getColor(R.color.splashFragmentBackground))

    }

    fun loadUrl(article: Article, position: Int) {

        val url = article.url
        var customTabs = CustomTabsIntent.Builder()
        customTabs.setToolbarColor(Color.parseColor("#C70000"))
        customTabs.setExitAnimations(this.context!!, 0, 0)
        customTabs.setStartAnimations(this.context!!, 0, 0)
        //PUT AS EXTRA TO OPEN FRAGMENT
        customTabs.addMenuItem(
            " Back To News Field", PendingIntent.getActivity(
                this.context, 0, Intent(
                    this.context,
                    MainActivity::class.java
                ), 0
            )
        )
        var intent2 = Intent(this.context, MainActivity::class.java)
        customTabs.addMenuItem(
            "See Bookmarked news", PendingIntent.getActivity(
                this.context, 0, intent2, 0
            )
        )


        // this is actually a bad idea
        //temp from line 162 - 179
        var heart = BitmapFactory.decodeResource(resources, R.drawable.heart)
        var shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
        shareIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            "Sent from NewsApp download News app, its available on play store"
        )
        shareIntent.putExtra(Intent.EXTRA_TEXT, article.url)
        var pendingIntent = PendingIntent.getActivity(
            this.context,
            100,
            shareIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        customTabs.setActionButton(
            heart,
            "Bookmark",
            createPendingIntent(BroadCastReceiver.ACTION_ACTION_BUTTON, position),
            true
        )

        // shares the HTML version of the open URL through
        customTabs.addDefaultShareMenuItem()
        var intent = customTabs.build()
        intent.launchUrl(this.context, Uri.parse(url))

    }

    private fun createPendingIntent(actionId: Int, articlePosition: Int): PendingIntent {
        var actionIntent = Intent(this.context, BroadCastReceiver::class.java)
        actionIntent.putExtra(BroadCastReceiver.KEY_ACTION_SOURCE, actionId)
        actionIntent.putExtra(BroadCastReceiver.ARTICLE, articlePosition)
        return PendingIntent.getBroadcast(
            activity?.applicationContext,
            actionId,
            actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    }

    lateinit var article: Article

    class BroadCastReceiver : BroadcastReceiver() {

        companion object {
            val KEY_ACTION_SOURCE = "com.mubaracktahir.news.ui.home"
            val ACTION_ACTION_BUTTON = 1
            val ACTION_MENU_ITEM = 2
            val ACTION_TOOLBAR = 3
            val ARTICLE = "4"
        }

        override fun onReceive(context: Context?, p1: Intent?) {
            val ul = p1?.dataString
            var articlePosition = p1?.getIntExtra("4", -1)
            if (p1 != null)
                takeAction(
                    context!!,
                    p1.getIntExtra(KEY_ACTION_SOURCE, -1),
                    " article position: $articlePosition ${ul!!}"
                )
        }

        private fun takeAction(context: Context, actionId: Int, url: String) {
            when (actionId) {
                ACTION_ACTION_BUTTON -> bookMarkArticle(context)
                ACTION_MENU_ITEM -> "ACTION_MENU_ITEM"
                ACTION_TOOLBAR -> "ACTION_TOOLBAR"
                else -> "a ghost button has been triggered $url"
            }

        }

        private fun bookMarkArticle(context: Context) {
            Toast.makeText(
                context,
                " bookMarked Successfully!!",
                Toast.LENGTH_LONG
            ).show()

        }
    }
}

