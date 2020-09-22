package com.example.currencyconverter.data

import com.squareup.moshi.Json

data class Currency(

    @Json(name = "sifra_valute")
    val id: String,

    @Json(name = "datum_primjene")
    val date: String,

    @Json(name = "valuta")
    val currencyName: String,

    @Json(name = "kupovni_tecaj")
    val buyingRate: String,

    @Json(name = "srednji_tecaj")
    val middleRate: String,

    @Json(name = "prodajni_tecaj")
    val sellingRate: String

)