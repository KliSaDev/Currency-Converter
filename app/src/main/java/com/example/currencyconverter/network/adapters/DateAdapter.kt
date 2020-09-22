package com.example.currencyconverter.network.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class DateAdapter {

    companion object {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-DD", Locale.GERMANY)
    }

    @FromJson
    fun fromJson(stringDate: String): ZonedDateTime {
        return ZonedDateTime.from(formatter.parse(stringDate))
    }

    @ToJson
    fun toJson(dateTime: ZonedDateTime): String {
        return dateTime.format(formatter)
    }
}