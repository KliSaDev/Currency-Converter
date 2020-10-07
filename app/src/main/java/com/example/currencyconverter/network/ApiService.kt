package com.example.currencyconverter.network

import com.example.currencyconverter.data.models.Currency
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    fun getAllCurrencies(): Single<List<Currency>>

    @GET(".")
    fun getCurrenciesByDate(
        @Query("datum-primjene-od") dateFrom: String,
        @Query("datum-primjene-do") dateTo: String
    ) : Single<List<Currency>>
}