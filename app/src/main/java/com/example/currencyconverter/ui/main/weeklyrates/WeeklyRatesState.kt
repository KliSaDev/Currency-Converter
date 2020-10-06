package com.example.currencyconverter.ui.main.weeklyrates

import com.example.currencyconverter.data.models.DailyCurrencyValue

data class WeeklyRatesState(
    val dailyValues: List<DailyCurrencyValue>
)

class WeeklyRatesEvent