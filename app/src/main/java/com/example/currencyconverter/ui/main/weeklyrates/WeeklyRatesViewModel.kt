package com.example.currencyconverter.ui.main.weeklyrates

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.BaseViewModel
import com.example.currencyconverter.data.CurrencyPreferences
import com.example.currencyconverter.data.CurrencyPreferences.Companion.KEY_SELECTED_CURRENCY_NAME
import com.example.currencyconverter.data.models.Currency
import com.example.currencyconverter.data.repositories.CurrencyRepository
import com.example.currencyconverter.di.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import timber.log.Timber
import javax.inject.Inject

class WeeklyRatesViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val preferences: CurrencyPreferences
) : BaseViewModel<WeeklyRatesState, WeeklyRatesEvent>() {

    private lateinit var selectedFromCurrency: Currency

    fun init() {
        Timber.d("${WeeklyRatesViewModel::class.simpleName} initialized")
        setSelectedCurrency(findCurrencyByName(preferences.getString(KEY_SELECTED_CURRENCY_NAME)))
        setupState()
    }

    fun onNewCurrencySelected(newCurrencyName: String) {
        setSelectedCurrency(findCurrencyByName(newCurrencyName))
        preferences.saveString(KEY_SELECTED_CURRENCY_NAME, newCurrencyName)
        setupState()
    }

    private fun setupState() {
        viewState = WeeklyRatesState(selectedFromCurrency, selectedFromCurrency.dailyValues)
    }

    private fun findCurrencyByName(name: String): Currency? {
        return currencyRepository.getAllCurrencies().find {
            it.currencyName == name
        }
    }

    private fun setSelectedCurrency(currency: Currency?) {
        if (currency != null) selectedFromCurrency = currency
    }
}

@Module
abstract class WeeklyRatesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeeklyRatesViewModel::class)
    abstract fun bindWeeklyRatesViewModel(viewModel: WeeklyRatesViewModel): ViewModel
}