package com.example.currencyconverter.ui.main.convert

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.BaseViewModel
import com.example.currencyconverter.data.models.Currency
import com.example.currencyconverter.data.models.DailyCurrencyValue
import com.example.currencyconverter.data.repositories.CurrencyRepository
import com.example.currencyconverter.di.annotations.ViewModelKey
import com.example.currencyconverter.network.interactors.GetAllCurrenciesInteractor
import com.example.currencyconverter.network.observers.ErrorHandlingSingleObserver
import com.example.currencyconverter.util.DEFAULT_FROM_CURRENCY_VALUE
import com.example.currencyconverter.util.DEFAULT_TO_CURRENCY_VALUE
import com.example.currencyconverter.util.MAX_DAILY_VALUES
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
    private var fromValue: String = DEFAULT_FROM_CURRENCY_VALUE.toString()
    private var toValue: String = DEFAULT_TO_CURRENCY_VALUE.toString()

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
            this.fromValue = fromValue
            this.toValue = convertValue(fromValue)
            viewState = viewState?.copy(
                fromValue = fromValue,
                toValue = toValue
            )
        }
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
            toValue = convertValue(fromValue)
        )
    }

    private fun getCurrenciesFromAPI() {
        getAllCurrenciesInteractor.execute().subscribe(object : ErrorHandlingSingleObserver<List<Currency>> {
                override fun onSuccess(updatedCurrencies: List<Currency>) {
                    insertCurrenciesInDatabase(updatedCurrencies)
                }
            })
    }

    private fun insertCurrenciesInDatabase(currencies: List<Currency>) {
        currencies.forEachIndexed { index, currency ->
            currencyRepository.insertCurrency(
                setDailyValues(currency),
                // We just want to setup state when the last Currency is inserted.
                { if (index == currencies.size - 1) { setupState() } }
            )
        }
    }

    private fun setDailyValues(currency: Currency): Currency {
        val dailyValues = currencyRepository.getDailyCurrencyValues(currency.id).toMutableList()
        dailyValues.add(
            DailyCurrencyValue(
                date = currency.date,
                middleRate = currency.middleRate
            )
        )

        // We only want to show 7 values. If the list contains more than that, we remove
        // the oldest value, which is first in the list.
        if (dailyValues.size > MAX_DAILY_VALUES) {
            dailyValues.removeAt(0)
        }
        currency.dailyValues = dailyValues
        return currency
    }

    private fun setupState() {
        selectedFromCurrency = currencyRepository.getTopmostCurrency()
        this.toValue = selectedFromCurrency.middleRate.toString()
        viewState = ConvertCurrencyState(
            selectedFromCurrency = selectedFromCurrency,
            fromValue = fromValue,
            toValue = toValue
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