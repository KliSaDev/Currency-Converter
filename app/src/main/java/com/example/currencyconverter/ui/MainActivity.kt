package com.example.currencyconverter.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.BaseActivity
import com.example.currencyconverter.R

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.init()
    }
}