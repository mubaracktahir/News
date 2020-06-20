package com.mubaracktahir.news.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mubaracktahir.news.R
import io.reactivex.disposables.CompositeDisposable

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {
    var disposable = CompositeDisposable()
    lateinit var logop: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.splash_fragment, container, false)
        logop = view.findViewById<ImageView>(R.id.logo)

        var animation = AnimationUtils.loadAnimation(context, R.anim.blink_anim)
        logop.startAnimation(animation)
        Handler().postDelayed({
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment)
        }, 2500)
        return view
    }

}
