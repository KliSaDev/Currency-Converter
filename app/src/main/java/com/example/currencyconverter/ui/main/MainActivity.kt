package com.example.currencyconverter.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.example.currencyconverter.BaseActivity
import com.example.currencyconverter.R
import com.example.currencyconverter.di.ViewModelProviderFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private val viewModel by viewModels<MainViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.init()
    }
}