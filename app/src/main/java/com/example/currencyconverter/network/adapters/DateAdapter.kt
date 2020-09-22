package com.example.currencyconverter.network.adapters

import com.squareup.moshi.FromJson
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {

    @FromJson
    fun fromJson(stringDate: String) {
        var formatter = SimpleDateFormat("YYYY-MM-DD", Locale.GERMANY)
        // TODO: finish implementation
    }
}