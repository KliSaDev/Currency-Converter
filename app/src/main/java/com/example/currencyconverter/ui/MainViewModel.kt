package com.example.currencyconverter.ui

import com.example.currencyconverter.BaseViewModel
import timber.log.Timber

class MainViewModel : BaseViewModel<MainState, MainEvent>() {

    fun init() {
        Timber.d("${MainViewModel::class.simpleName} initialized")
    }
}

sealed class MainState
sealed class MainEvent