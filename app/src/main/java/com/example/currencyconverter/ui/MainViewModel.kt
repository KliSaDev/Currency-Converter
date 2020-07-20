package com.example.currencyconverter.ui

import com.example.currencyconverter.BaseViewModel
import com.squareup.moshi.Moshi
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val moshi: Moshi
) : BaseViewModel<MainState, MainEvent>() {

    fun init() {
        val testdagger = moshi.newBuilder().build()
    }
}

sealed class MainState
sealed class MainEvent