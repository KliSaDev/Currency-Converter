package com.example.currencyconverter.ui.main.convert

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.BaseViewModel
import com.example.currencyconverter.di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import timber.log.Timber
import javax.inject.Inject

class ConvertCurrencyViewModel @Inject constructor() : BaseViewModel<ConvertCurrencyState, ConvertCurrencyEvent>() {

    val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun init() {
        Timber.d("${ConvertCurrencyViewModel::class.simpleName} initialized")
    }
}

@Module
abstract class ConvertCurrencyViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ConvertCurrencyViewModel::class)
    abstract fun bindConvertCurrencyViewModel(viewModel: ConvertCurrencyViewModel): ViewModel
}