package com.example.currencyconverter.network.adapters

import androidx.room.TypeConverter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal

class BigDecimalAdapter {

    companion object {

        @TypeConverter
        @JvmStatic
        @FromJson
        fun fromJson(currencyValue: String): BigDecimal {
            return if (currencyValue.contains(Regex("^[0-9]\\d*(,\\d+)?\$"))) {
                BigDecimal(currencyValue.replace(",", "."))
            } else {
                BigDecimal(currencyValue)
            }
        }

        @TypeConverter
        @JvmStatic
        @ToJson
        fun toJson(currencyValue: BigDecimal): String {
            return currencyValue.toString()
        }
    }
}