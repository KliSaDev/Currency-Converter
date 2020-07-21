package com.example.currencyconverter.ui.main

import com.example.currencyconverter.BaseViewModel
import dagger.Binds
import dagger.Module
import timber.log.Timber

class MainViewModel : BaseViewModel<MainState, MainEvent>() {

    fun init() {
        Timber.d("${MainViewModel::class.simpleName} initialized")
    }
}

@Module
abstract class MainViewModelModule {

    @Binds
    abstract fun bindMainViewModel(viewModel: MainViewModel) : MainViewModel
}