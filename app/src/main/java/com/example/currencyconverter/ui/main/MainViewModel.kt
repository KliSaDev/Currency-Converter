package com.example.currencyconverter.ui.main

import com.example.currencyconverter.BaseViewModel
import com.example.currencyconverter.di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import timber.log.Timber

class MainViewModel : BaseViewModel<MainState, MainEvent>() {

    fun init() {
        Timber.d("${MainViewModel::class.simpleName} initialized")
    }
}

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel) : MainViewModel
}