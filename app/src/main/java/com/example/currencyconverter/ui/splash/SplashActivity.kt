package com.example.currencyconverter.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.main.MainActivity
import com.example.currencyconverter.util.ANIMATION_DURATION
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        setContentView(R.layout.activity_splash)

        setupUi()
    }

    private fun setupUi() {
        logoImage.pathAnimator
            .duration(ANIMATION_DURATION)
            .listenerEnd { navigateToMain() }
            .start()

        titleLabel.animation = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom).apply {
            duration = ANIMATION_DURATION.toLong()
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}