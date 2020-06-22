package com.mubaracktahir.news.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mubaracktahir.news.R
import com.mubaracktahir.news.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this@MainActivity,
            R.layout.activity_main
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setTransparentStatusBar()
        setupNavigation()
        btn_nav.setupWithNavController(navController)
    }

    private fun setTransparentStatusBar() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.parseColor("#C70000")
        }
    }

    private fun setupNavigation() {

        navController = findNavController(R.id.nav_host_fragment_container)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    binding.btnNav.visibility = View.GONE
                }
                R.id.homeFragment -> {
                    binding.btnNav.visibility = View.VISIBLE
                }
                else -> {
                    binding.btnNav.visibility = View.VISIBLE

                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
