package com.example.currencyconverter.ui.main

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.BaseViewModel
import com.example.currencyconverter.di.annotations.ViewModelKey
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val moshi: Moshi
) : BaseViewModel<MainState, MainEvent>() {

    fun init() {
        Timber.d("${MainViewModel::class.simpleName} initialized")
    }
}

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel) : ViewModel
}