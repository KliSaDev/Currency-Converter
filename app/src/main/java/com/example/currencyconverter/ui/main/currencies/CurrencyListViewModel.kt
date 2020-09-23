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
import timber.log.Timber
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(
    private val getAllCurrenciesInteractor: GetAllCurrenciesInteractor,
    private val currencyRepository: CurrencyRepository
) : BaseViewModel<CurrencyListState, CurrencyListEvent>() {

    fun init() {
        Timber.d("${CurrencyListViewModel::class.simpleName} initialized")

        getAllCurrenciesInteractor.execute().subscribe(object : ErrorHandlingSingleObserver<List<Currency>> {
                override fun onSuccess(currencies: List<Currency>) {
                    viewState = CurrencyListState(currencies)
                    currencies.forEach { currency ->
                        currencyRepository.insertCurrency(currency)
                    }
                }
            })
    }
}

@Module
abstract class CurrencyListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyListViewModel::class)
    abstract fun bindCurrencyListViewModel(viewModel: CurrencyListViewModel): ViewModel
}