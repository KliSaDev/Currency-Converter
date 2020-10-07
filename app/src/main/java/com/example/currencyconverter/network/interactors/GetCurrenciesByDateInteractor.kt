package com.example.currencyconverter.network.interactors

import com.example.currencyconverter.data.models.Currency
import com.example.currencyconverter.data.models.GetCurrenciesByDateParams
import com.example.currencyconverter.network.ApiService
import com.example.currencyconverter.util.applySchedulers
import io.reactivex.Single
import javax.inject.Inject

class GetCurrenciesByDateInteractor @Inject constructor(
    private val apiService: ApiService
) {

    fun execute(params: GetCurrenciesByDateParams): Single<List<Currency>> {
        return apiService.getCurrenciesByDate(params.dateFrom, params.dateTo).applySchedulers()
    }
}
