package com.example.currencyconverter.util

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun LocalDate.getDay(): String {
    return this.format(DateTimeFormatter.ofPattern("EEE"))
}