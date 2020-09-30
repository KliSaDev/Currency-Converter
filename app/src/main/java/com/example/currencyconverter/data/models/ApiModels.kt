package com.example.currencyconverter.data.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate
import java.math.BigDecimal

@Parcelize
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

) : Parcelable