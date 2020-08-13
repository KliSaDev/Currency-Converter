package com.example.currencyconverter.ui.main.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.BaseViewModel
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.data.repositories.CurrencyRepository
import com.example.currencyconverter.di.annotations.ViewModelKey
import com.example.currencyconverter.network.interactors.GetAllCurrenciesInteractor
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(
    private val getAllCurrenciesInteractor: GetAllCurrenciesInteractor,
    private val currencyRepository: CurrencyRepository
) : BaseViewModel<CurrencyListState, CurrencyListEvent>() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun init() {
        Timber.d("${CurrencyListViewModel::class.simpleName} initialized")

        getAllCurrenciesInteractor.execute().subscribe(object : SingleObserver<List<Currency>> {
            override fun onSuccess(currencies: List<Currency>) {
                Timber.d("${CurrencyListViewModel::class.simpleName} repository ${currencyRepository.hashCode()}")
                viewState = CurrencyListState(currencies)
                currencies.forEach { currency ->
                    currencyRepository.insertCurrency(currency)
                }
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
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