package com.example.currencyconverter.network.adapters

import androidx.room.TypeConverter
import com.example.currencyconverter.data.models.MoshiProvider
import com.example.currencyconverter.data.models.DailyCurrencyValue
import com.squareup.moshi.*

class DailyCurrencyValueAdapter {

    companion object {

        private val moshi = MoshiProvider.moshi
        val type = Types.newParameterizedType(List::class.java, DailyCurrencyValue::class.java)
        val adapter: JsonAdapter<List<DailyCurrencyValue>> = moshi.adapter(type)

        @TypeConverter
        @JvmStatic
        @FromJson
        fun fromJson(listAsString: String): List<DailyCurrencyValue>? {
            return adapter.fromJson(listAsString)
        }

        @TypeConverter
        @JvmStatic
        @ToJson
        fun toJson(list: List<DailyCurrencyValue>): String {
            return adapter.toJson(list)
        }
    }
}