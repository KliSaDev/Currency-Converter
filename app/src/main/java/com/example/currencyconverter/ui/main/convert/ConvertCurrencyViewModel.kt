package com.example.currencyconverter.ui.main.convert

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.BaseViewModel
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.data.repositories.CurrencyRepository
import com.example.currencyconverter.di.annotations.ViewModelKey
import com.example.currencyconverter.util.DEFAULT_FROM_CURRENCY_VALUE
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

class ConvertCurrencyViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : BaseViewModel<ConvertCurrencyState, ConvertCurrencyEvent>() {

    private lateinit var selectedFromCurrency: Currency

    fun init() {
        Timber.d("${ConvertCurrencyViewModel::class.simpleName} initialized")

        selectedFromCurrency = currencyRepository.getTopmostCurrency()
        viewState = ConvertCurrencyState(
            selectedFromCurrency = selectedFromCurrency,
            fromValue = DEFAULT_FROM_CURRENCY_VALUE.toString(),
            toValue = String.format("%4f", selectedFromCurrency.middleRate)
        )
    }

    fun onCalculateClicked(fromValue: String) {
        viewState = viewState?.copy(
            fromValue = fromValue,
            toValue = convertValue(fromValue)
        )
    }

    private fun convertValue(fromValue: String): String {
        return BigDecimal(fromValue).multiply(selectedFromCurrency.middleRate).toString()
    }
}

@Module
abstract class ConvertCurrencyViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ConvertCurrencyViewModel::class)
    abstract fun bindConvertCurrencyViewModel(viewModel: ConvertCurrencyViewModel): ViewModel
}