package com.example.currencyconverter.ui.main.weeklyrates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import timber.log.Timber
import javax.inject.Inject

class WeeklyRatesViewModel @Inject constructor() : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun init() {
        Timber.d("${WeeklyRatesViewModel::class.simpleName} initialized")
    }
}

@Module
abstract class WeeklyRatesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeeklyRatesViewModel::class)
    abstract fun bindWeeklyRatesViewModel(viewModel: WeeklyRatesViewModel): ViewModel
}