package com.example.currencyconverter.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "currency_name") val currencyName: String,
    @ColumnInfo(name = "buying_rate") val buyingRate: String,
    @ColumnInfo(name = "middle_rate") val middleRate: String,
    @ColumnInfo(name = "selling_rate") val sellingRate: String?
)