package com.example.currencyconverter.ui.main.currencies

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.BaseViewModel
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.data.repositories.CurrencyRepository
import com.example.currencyconverter.di.annotations.ViewModelKey
import com.example.currencyconverter.network.interactors.GetAllCurrenciesInteractor
import com.example.currencyconverter.network.observers.ErrorHandlingSingleObserver
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.threeten.bp.LocalDate
import timber.log.Timber
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(
    private val getAllCurrenciesInteractor: GetAllCurrenciesInteractor,
    private val currencyRepository: CurrencyRepository
) : BaseViewModel<CurrencyListState, CurrencyListEvent>() {

    private var currencies: List<Currency> = emptyList()

    fun init() {
        Timber.d("${CurrencyListViewModel::class.simpleName} initialized")

        val isDatabaseEmpty = getCurrenciesFromDatabase().isNullOrEmpty()
        if (isDatabaseEmpty || shouldCurrenciesBeUpdated()) {
            getCurrenciesFromAPI()
        } else {
            currencies = getCurrenciesFromDatabase()
            showCurrencies(currencies)
        }
    }

    private fun getCurrenciesFromAPI() {
        getAllCurrenciesInteractor.execute().subscribe(object : ErrorHandlingSingleObserver<List<Currency>> {
                override fun onSuccess(updatedCurrencies: List<Currency>) {
                    currencies = updatedCurrencies
                    showCurrencies(updatedCurrencies)
                    insertCurrenciesInDatabase(updatedCurrencies)
                }
            })
    }

    private fun getCurrenciesFromDatabase(): List<Currency> {
        return currencyRepository.getAllCurrencies()
    }

    private fun shouldCurrenciesBeUpdated(): Boolean {
        val lastUpdatedDate = currencyRepository.getTopmostCurrency().date
        return LocalDate.now() != lastUpdatedDate
    }

    private fun showCurrencies(currencies: List<Currency>) {
        viewState = CurrencyListState(currencies)
    }

    private fun insertCurrenciesInDatabase(currencies: List<Currency>) {
        currencies.forEach { currency ->
            currencyRepository.insertCurrency(currency)
        }
    }
}

@Module
abstract class CurrencyListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyListViewModel::class)
    abstract fun bindCurrencyListViewModel(viewModel: CurrencyListViewModel): ViewModel
}