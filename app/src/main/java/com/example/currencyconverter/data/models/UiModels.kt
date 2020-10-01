package com.example.currencyconverter.data.models

import org.threeten.bp.LocalDate
import java.math.BigDecimal

class DailyCurrencyValue(
    val date: LocalDate,
    val middleRate: BigDecimal
)