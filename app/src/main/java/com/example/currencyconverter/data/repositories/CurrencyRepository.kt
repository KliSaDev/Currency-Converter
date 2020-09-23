package com.example.currencyconverter.data.repositories

import androidx.lifecycle.LiveData
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.db.CurrencyDatabase
import com.example.currencyconverter.db.CurrencyEntity
import com.example.currencyconverter.network.observers.ErrorHandlingCompletableObserver
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyDatabase: CurrencyDatabase
) {

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

    fun getAllCurrencies(): List<Currency> {
        val currencyEntities = currencyDatabase.currencyDao().getAllCurrencies()
            .subscribeOn(Schedulers.io())
            .blockingGet()

        val currencies = mutableListOf<Currency>()

        currencyEntities.mapTo(currencies) { currencyEntity ->
            Currency(
                id = currencyEntity.id,
                date = currencyEntity.date,
                currencyName = currencyEntity.currencyName,
                buyingRate = currencyEntity.buyingRate,
                middleRate = currencyEntity.middleRate,
                sellingRate = currencyEntity.sellingRate
            )
        }

        return currencies
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
}