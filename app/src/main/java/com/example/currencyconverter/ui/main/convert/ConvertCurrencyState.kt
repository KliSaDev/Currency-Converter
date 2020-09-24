package com.example.currencyconverter.ui.main.convert

import com.example.currencyconverter.data.Currency

data class ConvertCurrencyState(
    val selectedFromCurrency: Currency,
    val fromValue: String,
    val toValue: String
)

sealed class ConvertCurrencyEvent