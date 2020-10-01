package com.example.currencyconverter.data.models

import com.example.currencyconverter.network.adapters.BigDecimalAdapter
import com.example.currencyconverter.network.adapters.LocalDateAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiProvider {

    val moshi = Moshi.Builder()
        .add(LocalDateAdapter())
        .add(BigDecimalAdapter())
        .add(KotlinJsonAdapterFactory()) // For Kotlin data class serialization.
        .build()
}