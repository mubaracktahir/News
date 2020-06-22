package com.mubaracktahir.news.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mikhaellopez.rxanimation.RxAnimation
import com.mubaracktahir.news.R
import com.mubaracktahir.news.databinding.SplashFragmentBinding
import io.reactivex.disposables.CompositeDisposable

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {
   // var disposable = CompositeDisposable()
    lateinit var binding: SplashFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.splash_fragment, container, false)

        var animation = AnimationUtils.loadAnimation(context, R.anim.blink_anim)
        binding.logo.startAnimation(animation)
        Handler().postDelayed({
            view?.let { _view ->
                Navigation.findNavController(_view).navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }, 2500)
        return binding.root
    }
}
