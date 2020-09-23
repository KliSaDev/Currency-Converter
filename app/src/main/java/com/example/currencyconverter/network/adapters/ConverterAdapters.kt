package com.example.currencyconverter.network.adapters

import androidx.room.TypeConverter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

/**
 * This class contains all type converter methods needed for JSON serialization of custom and more
 * complex objects.
 */
class ConverterAdapters {

    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        @TypeConverter
        @JvmStatic
        @FromJson
        fun fromJson(date: String): LocalDate {
            return LocalDate.from(formatter.parse(date))
        }

        @TypeConverter
        @JvmStatic
        @ToJson
        fun toJson(date: LocalDate): String {
            return date.format(formatter)
        }
    }
}