package com.example.currencyconverter.ui.main.convert

import com.example.currencyconverter.data.models.Currency

data class ConvertCurrencyState(
    val selectedForeignCurrency: Currency,
    val selectedFromCurrencyLabel: String,
    val selectedToCurrencyLabel: String,
    val fromValue: String,
    val toValue: String
)

sealed class ConvertCurrencyEvent
object InvalidNumberInput : ConvertCurrencyEvent()
object NoInternetConnection : ConvertCurrencyEvent()