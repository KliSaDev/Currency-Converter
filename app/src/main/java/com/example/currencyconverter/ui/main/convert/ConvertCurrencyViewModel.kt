package com.example.currencyconverter.ui.main.convert

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.BaseViewModel
import com.example.currencyconverter.data.models.Currency
import com.example.currencyconverter.data.models.DailyCurrencyValue
import com.example.currencyconverter.data.models.GetCurrenciesByDateParams
import com.example.currencyconverter.data.repositories.CurrencyRepository
import com.example.currencyconverter.di.annotations.ViewModelKey
import com.example.currencyconverter.network.interactors.GetAllCurrenciesInteractor
import com.example.currencyconverter.network.interactors.GetCurrenciesByDateInteractor
import com.example.currencyconverter.network.observers.ErrorHandlingSingleObserver
import com.example.currencyconverter.util.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.threeten.bp.LocalDate
import timber.log.Timber
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class ConvertCurrencyViewModel @Inject constructor(
    private val getCurrenciesByDateInteractor: GetCurrenciesByDateInteractor,
    private val getAllCurrenciesInteractor: GetAllCurrenciesInteractor,
    private val currencyRepository: CurrencyRepository
) : BaseViewModel<ConvertCurrencyState, ConvertCurrencyEvent>() {

    private lateinit var selectedForeignCurrency: Currency
    private var fromValue: String = DEFAULT_FROM_CURRENCY_VALUE.toString()
    private var toValue: String = DEFAULT_TO_CURRENCY_VALUE.toString()
    private var isHrkFromCurrency = false

    fun init() {
        Timber.d("${ConvertCurrencyViewModel::class.simpleName} initialized")
        showProgress()

        val isDatabaseEmpty = getCurrenciesFromDatabase().isNullOrEmpty()
        if (isDatabaseEmpty) {
            getCurrenciesFromAPI(true)
        } else if (shouldCurrenciesBeUpdated()) {
            getCurrenciesFromAPI()
        } else {
            setupState()
            hideProgress()
        }
    }

    fun onCalculateClicked(fromValue: String) {
        if (fromValue.isEmpty()) {
            emitEvent(InvalidNumberInput)
        } else {
            this.fromValue = fromValue
            this.toValue = if (isHrkFromCurrency) convertSwitchedValue(fromValue) else convertValue(fromValue)
            val selectedFromCurrencyLabel = if (isHrkFromCurrency) BASE_CURRENCY_LABEL_HRK else selectedForeignCurrency.currencyName
            val selectedToCurrencyLabel = if (isHrkFromCurrency) selectedForeignCurrency.currencyName else BASE_CURRENCY_LABEL_HRK
            viewState = viewState?.copy(
                selectedForeignCurrency = selectedForeignCurrency,
                selectedFromCurrencyLabel = selectedFromCurrencyLabel,
                selectedToCurrencyLabel = selectedToCurrencyLabel,
                fromValue = fromValue,
                toValue = toValue
            )
        }
    }

    private fun convertValue(fromValue: String): String {
        val resultValue = BigDecimal(fromValue).multiply(selectedForeignCurrency.middleRate)
        return String.format(FORMAT_CURRENCY_LIST_RATES, resultValue)
    }

    private fun convertSwitchedValue(fromValue: String): String {
        return BigDecimal(fromValue).divide(
            selectedForeignCurrency.middleRate,
            SCALE_FOR_DIVIDED_VALUE,
            RoundingMode.HALF_UP).toString()
    }

    fun onNewCurrencySelected(newCurrencyName: String) {
        val newCurrency = currencyRepository.getAllCurrencies().find {
            it.currencyName == newCurrencyName
        }
        if (newCurrency != null) {
            selectedForeignCurrency = newCurrency
        }

        viewState = viewState?.copy(
            selectedFromCurrencyLabel = selectedForeignCurrency.currencyName,
            toValue = convertValue(fromValue)
        )
    }

    private fun getCurrenciesFromAPI(shouldGetCurrenciesByDate: Boolean = false) {
        getAllCurrenciesInteractor.execute().subscribe(object : ErrorHandlingSingleObserver<List<Currency>> {
                override fun onSuccess(updatedCurrencies: List<Currency>) {
                    insertCurrenciesInDatabase(updatedCurrencies)
                    if (shouldGetCurrenciesByDate) getCurrenciesByDate()
                    hideProgress()
                }

                override fun onError(e: Throwable) {
                    if (getCurrenciesFromDatabase().isNullOrEmpty()) {
                        handleException(e, NoInternetConnection)
                    } else {
                        setupState()
                    }
                    hideProgress()
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

    private fun getCurrenciesByDate() {
        val params = GetCurrenciesByDateParams(
            LocalDate.now().minusDays(DAYS_TO_SUBTRACT_FOR_WEEKLY_RATES).toString(),
            LocalDate.now().toString()
        )

        getCurrenciesByDateInteractor.execute(params).subscribe(object : ErrorHandlingSingleObserver<List<Currency>> {
            override fun onSuccess(currenciesByDate: List<Currency>) {
                currencyRepository.getAllCurrencies().forEach { currency ->
                    val currenciesThroughWeek = currenciesByDate.filter { currencyByDate ->
                        currencyByDate.id == currency.id
                    }

                    val dailyCurrencyValues = mutableListOf<DailyCurrencyValue>()
                    currenciesThroughWeek.mapTo(dailyCurrencyValues) { currencyThroughWeek ->
                        DailyCurrencyValue(
                            currencyThroughWeek.date,
                            currencyThroughWeek.middleRate
                        )
                    }

                    currencyRepository.updateCurrency(
                        currency.copy(dailyValues = dailyCurrencyValues)
                    )
                }
                hideProgress()
            }
        })
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
        selectedForeignCurrency = currencyRepository.getTopmostCurrency()
        this.toValue = String.format(FORMAT_CURRENCY_LIST_RATES, selectedForeignCurrency.middleRate)

        viewState = ConvertCurrencyState(
            selectedForeignCurrency = selectedForeignCurrency,
            selectedFromCurrencyLabel = selectedForeignCurrency.currencyName,
            selectedToCurrencyLabel = "HRK",
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

    fun onSwitchCurrencies() {
        isHrkFromCurrency = !isHrkFromCurrency
    }
}

@Module
abstract class ConvertCurrencyViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ConvertCurrencyViewModel::class)
    abstract fun bindConvertCurrencyViewModel(viewModel: ConvertCurrencyViewModel): ViewModel
}