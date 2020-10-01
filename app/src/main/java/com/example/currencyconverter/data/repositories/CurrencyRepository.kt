package com.example.currencyconverter.data.repositories

import com.example.currencyconverter.data.models.Currency
import com.example.currencyconverter.db.CurrencyDatabase
import com.example.currencyconverter.db.CurrencyEntity
import com.example.currencyconverter.network.observers.ErrorHandlingCompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyDatabase: CurrencyDatabase
) {

    private val allCurrencies: List<Currency> = emptyList()

    fun insertCurrency(currency: Currency) {
        currencyDatabase.currencyDao().insertCurrency(currency.toCurrencyEntity())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ErrorHandlingCompletableObserver {
                override fun onComplete() {
                    Timber.d("Currency ${currency.currencyName} ${currency.id} added.")
                }
            })
    }

    fun getCurrencyById(id: String): Currency {
        return currencyDatabase.currencyDao().getCurrencyById(id)
            .subscribeOn(Schedulers.io()).blockingGet().toCurrency()
    }

    fun getTopmostCurrency(): Currency {
        return getAllCurrencies()[0]
    }

    fun getAllCurrencies(): List<Currency> {
        return if (allCurrencies.isEmpty()) {
            val currencyEntities = currencyDatabase.currencyDao().getAllCurrencies()
                .subscribeOn(Schedulers.io())
                .blockingGet()

            currencyEntities.map { currencyEntity -> currencyEntity.toCurrency() }.toList()
        } else {
            allCurrencies
        }
    }

    private fun Currency.toCurrencyEntity(): CurrencyEntity {
        return CurrencyEntity(
            id = this.id,
            date = this.date,
            currencyName = this.currencyName,
            buyingRate = this.buyingRate,
            middleRate = this.middleRate,
            sellingRate = this.sellingRate
        )
    }

    private fun CurrencyEntity.toCurrency(): Currency {
        return Currency(
            id = this.id,
            date = this.date,
            currencyName = this.currencyName,
            buyingRate = this.buyingRate,
            middleRate = this.middleRate,
            sellingRate = this.sellingRate
        )
    }
}