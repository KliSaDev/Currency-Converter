package com.example.currencyconverter.network.adapters

import androidx.room.TypeConverter
import com.example.currencyconverter.data.models.RateValueOfTheDate
import com.squareup.moshi.*

class RateValueOfTheDateAdapter {

    companion object {

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, RateValueOfTheDate::class.java)
        val adapter: JsonAdapter<List<RateValueOfTheDate>> = moshi.adapter(type)

        @TypeConverter
        @JvmStatic
        @FromJson
        fun fromJson(listAsString: String): List<RateValueOfTheDate>? {
            return adapter.fromJson(listAsString)
        }

        @TypeConverter
        @JvmStatic
        @ToJson
        fun toJson(list: List<RateValueOfTheDate>): String {
            return adapter.toJson(list)
        }
    }
}