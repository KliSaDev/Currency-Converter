package com.example.currencyconverter.di.main.currencies

import com.example.currencyconverter.ui.adapters.CurrencyListAdapter
import dagger.Module
import dagger.Provides

@Module
class CurrencyListModule {

    companion object {
        @Provides
        fun provideCurrencyListAdapter() = CurrencyListAdapter()
    }
}