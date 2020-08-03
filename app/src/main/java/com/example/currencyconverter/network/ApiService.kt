package com.example.currencyconverter.network

import com.example.currencyconverter.data.Currency
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET(".")
    fun getAllCurrencies(): Single<List<Currency>>
}