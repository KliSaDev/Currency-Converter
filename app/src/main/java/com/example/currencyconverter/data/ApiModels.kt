package com.example.currencyconverter.data

import com.squareup.moshi.Json
import org.threeten.bp.LocalDate
import java.math.BigDecimal

data class Currency(

    @Json(name = "sifra_valute")
    val id: String,

    @Json(name = "datum_primjene")
    val date: LocalDate,

    @Json(name = "valuta")
    val currencyName: String,

    @Json(name = "kupovni_tecaj")
    val buyingRate: BigDecimal,

    @Json(name = "srednji_tecaj")
    val middleRate: BigDecimal,

    @Json(name = "prodajni_tecaj")
    val sellingRate: BigDecimal

)