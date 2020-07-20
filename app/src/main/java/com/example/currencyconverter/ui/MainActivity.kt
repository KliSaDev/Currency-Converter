package com.example.currencyconverter.ui

import android.os.Bundle
import com.example.currencyconverter.BaseActivity
import com.example.currencyconverter.R
import timber.log.Timber

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("onCreate, Main")
    }
}