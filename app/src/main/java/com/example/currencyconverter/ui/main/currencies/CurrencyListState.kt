package com.example.currencyconverter.ui.main.currencies

import com.example.currencyconverter.data.Currency

data class CurrencyListState(
    val currencies: List<Currency>
)

sealed class CurrencyListEvent