package com.example.currencyconverter.data

import com.squareup.moshi.Json

data class Currency(

    @Json(name = "Šifra valute")
    val id: String,

    @Json(name = "Datum primjene")
    val date: String,

    @Json(name = "Valuta")
    val currency: String,

    @Json(name = "Kupovni za devize")
    val buyingRate: String,

    @Json(name = "Srednji za devize")
    val middleRate: String,

    @Json(name = "Prodajni za devize")
    val sellingRate: String

)