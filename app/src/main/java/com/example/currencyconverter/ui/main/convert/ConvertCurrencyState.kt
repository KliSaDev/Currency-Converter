package com.example.currencyconverter.ui.main.convert

import com.example.currencyconverter.data.models.Currency

data class ConvertCurrencyState(
    val selectedFromCurrency: Currency,
    val fromValue: String,
    val toValue: String,
    val areCurrenciesSwitched: Boolean
)

sealed class ConvertCurrencyEvent
object InvalidNumberInput : ConvertCurrencyEvent()
object NoInternetConnection : ConvertCurrencyEvent()