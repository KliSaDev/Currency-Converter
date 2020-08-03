package com.example.currencyconverter.di.network

import com.example.currencyconverter.network.ApiService
import com.example.currencyconverter.network.interactors.GetAllCurrenciesInteractor
import dagger.Module
import dagger.Provides

@Module
abstract class InteractorsModule {

    companion object {
        @Provides
        fun bindGetAllCurrenciesInteractor(apiService: ApiService): GetAllCurrenciesInteractor {
            return GetAllCurrenciesInteractor(apiService)
        }
    }
}