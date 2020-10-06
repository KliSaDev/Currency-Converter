package com.example.currencyconverter.ui.main.weeklyrates

import com.example.currencyconverter.data.models.Currency
import com.example.currencyconverter.data.models.DailyCurrencyValue

data class WeeklyRatesState(
    val selectedFromCurrency: Currency,
    val dailyValues: List<DailyCurrencyValue>
)

class WeeklyRatesEvent