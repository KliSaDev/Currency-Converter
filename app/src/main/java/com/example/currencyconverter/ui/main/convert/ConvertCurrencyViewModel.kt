package com.example.currencyconverter.ui.main.convert

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.BaseViewModel
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.data.repositories.CurrencyRepository
import com.example.currencyconverter.di.annotations.ViewModelKey
import com.example.currencyconverter.network.interactors.GetAllCurrenciesInteractor
import com.example.currencyconverter.network.observers.ErrorHandlingSingleObserver
import com.example.currencyconverter.util.DEFAULT_FROM_CURRENCY_VALUE
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.threeten.bp.LocalDate
import timber.log.Timber
import java.math.BigDecimal
import javax.inject.Inject

class ConvertCurrencyViewModel @Inject constructor(
    private val getAllCurrenciesInteractor: GetAllCurrenciesInteractor,
    private val currencyRepository: CurrencyRepository
) : BaseViewModel<ConvertCurrencyState, ConvertCurrencyEvent>() {

    private lateinit var selectedFromCurrency: Currency

    fun init() {
        Timber.d("${ConvertCurrencyViewModel::class.simpleName} initialized")

        val isDatabaseEmpty = getCurrenciesFromDatabase().isNullOrEmpty()
        if (isDatabaseEmpty || shouldCurrenciesBeUpdated()) {
            getCurrenciesFromAPI()
        } else {
            setupState()
        }
    }

    fun onCalculateClicked(fromValue: String) {
        if (fromValue.isEmpty()) {
            emitEvent(InvalidNumberInput)
        } else {
            viewState = viewState?.copy(
                fromValue = fromValue,
                toValue = convertValue(fromValue)
            )
        }
    }

    fun onNewCurrencySelected(newCurrencyName: String) {
        val newCurrency = currencyRepository.getAllCurrencies().find {
            it.currencyName == newCurrencyName
        }
        if (newCurrency != null) { selectedFromCurrency = newCurrency }

        viewState = viewState?.copy(selectedFromCurrency = selectedFromCurrency)
    }

    private fun getCurrenciesFromAPI() {
        getAllCurrenciesInteractor.execute().subscribe(object : ErrorHandlingSingleObserver<List<Currency>> {
                override fun onSuccess(updatedCurrencies: List<Currency>) {
                    insertCurrenciesInDatabase(updatedCurrencies)
                    setupState()
                }
            })
    }

    private fun insertCurrenciesInDatabase(currencies: List<Currency>) {
        currencies.forEach { currency ->
            currencyRepository.insertCurrency(currency)
        }
    }

    private fun setupState() {
        selectedFromCurrency = currencyRepository.getTopmostCurrency()
        viewState = ConvertCurrencyState(
            selectedFromCurrency = selectedFromCurrency,
            fromValue = DEFAULT_FROM_CURRENCY_VALUE.toString(),
            toValue = selectedFromCurrency.middleRate.toString()
        )
    }

    private fun getCurrenciesFromDatabase(): List<Currency> {
        return currencyRepository.getAllCurrencies()
    }

    private fun shouldCurrenciesBeUpdated(): Boolean {
        val lastUpdatedDate = currencyRepository.getTopmostCurrency().date
        return LocalDate.now() != lastUpdatedDate
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