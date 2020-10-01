package com.example.currencyconverter.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate
import java.math.BigDecimal

@Parcelize
class DailyCurrencyValue(
    val date: LocalDate,
    val middleRate: BigDecimal
) : Parcelable