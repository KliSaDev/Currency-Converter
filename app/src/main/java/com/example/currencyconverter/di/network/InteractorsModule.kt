package com.example.currencyconverter.di.network

import com.example.currencyconverter.network.ApiService
import com.example.currencyconverter.network.interactors.GetAllCurrenciesInteractor
import com.example.currencyconverter.network.interactors.GetCurrenciesByDateInteractor
import dagger.Module
import dagger.Provides

@Module
abstract class InteractorsModule {

    companion object {
        @JvmStatic
        @Provides
        fun bindGetAllCurrenciesInteractor(apiService: ApiService): GetAllCurrenciesInteractor {
            return GetAllCurrenciesInteractor(apiService)
        }

        @JvmStatic
        @Provides
        fun bindGetCurrenciesByDateInteractor(apiService: ApiService): GetCurrenciesByDateInteractor {
            return GetCurrenciesByDateInteractor(apiService)
        }
    }
}