package com.example.currencyconverter.data

import com.squareup.moshi.Json

data class Currency(

    @Json(name = "Datum primjene")
    private val date: String,

    @Json(name = "Valuta")
    private val currency: String,

    @Json(name = "Kupovni za devize")
    private val buyingRate: String,

    @Json(name = "Srednji za devize")
    private val middleRate: String,

    @Json(name = "Prodajni za devize")
    private val sellingRate: String

)