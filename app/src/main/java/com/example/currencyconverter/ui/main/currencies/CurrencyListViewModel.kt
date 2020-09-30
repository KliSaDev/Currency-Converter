package com.example.currencyconverter.ui.main.currencies

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

class CurrencyListViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : BaseViewModel<CurrencyListState, CurrencyListEvent>() {

    fun init() {
        Timber.d("${CurrencyListViewModel::class.simpleName} initialized")

        showCurrencies(getCurrenciesFromDatabase())
    }

    private fun getCurrenciesFromDatabase(): List<Currency> {
        return currencyRepository.getAllCurrencies()
    }

    private fun showCurrencies(currencies: List<Currency>) {
        viewState = CurrencyListState(currencies)
    }
}

@Module
abstract class CurrencyListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyListViewModel::class)
    abstract fun bindCurrencyListViewModel(viewModel: CurrencyListViewModel): ViewModel
}