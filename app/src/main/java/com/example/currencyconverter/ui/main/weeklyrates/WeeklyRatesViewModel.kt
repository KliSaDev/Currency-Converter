package com.example.currencyconverter.ui.main.weeklyrates

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.BaseViewModel
import com.example.currencyconverter.data.models.Currency
import com.example.currencyconverter.data.repositories.CurrencyRepository
import com.example.currencyconverter.di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import timber.log.Timber
import javax.inject.Inject

class WeeklyRatesViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : BaseViewModel<WeeklyRatesState, WeeklyRatesEvent>() {

    private lateinit var selectedFromCurrency: Currency

    fun init() {
        Timber.d("${WeeklyRatesViewModel::class.simpleName} initialized")
        selectedFromCurrency = currencyRepository.getTopmostCurrency()
        viewState = WeeklyRatesState(selectedFromCurrency, selectedFromCurrency.dailyValues)
    }

    fun onNewCurrencySelected(newCurrencyName: String) {
        val newCurrency = currencyRepository.getAllCurrencies().find {
            it.currencyName == newCurrencyName
        }
        if (newCurrency != null) {
            selectedFromCurrency = newCurrency
        }

        viewState = viewState?.copy(
            selectedFromCurrency = selectedFromCurrency,
            dailyValues = selectedFromCurrency.dailyValues
        )
    }
}

@Module
abstract class WeeklyRatesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeeklyRatesViewModel::class)
    abstract fun bindWeeklyRatesViewModel(viewModel: WeeklyRatesViewModel): ViewModel
}