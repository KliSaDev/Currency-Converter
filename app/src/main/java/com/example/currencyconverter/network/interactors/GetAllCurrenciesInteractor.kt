package com.example.currencyconverter.network.interactors

import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.network.ApiService
import com.example.currencyconverter.util.applySchedulers
import io.reactivex.Single
import javax.inject.Inject

class GetAllCurrenciesInteractor @Inject constructor(
    private val apiService: ApiService
) {

    fun execute(): Single<List<Currency>> {
        return apiService.getAllCurrencies().applySchedulers()
    }
}

