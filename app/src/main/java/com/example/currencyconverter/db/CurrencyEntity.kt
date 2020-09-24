package com.example.currencyconverter.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate
import java.math.BigDecimal

@Entity(tableName = "currency_entity")
data class CurrencyEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "currency_name") val currencyName: String,
    @ColumnInfo(name = "buying_rate") val buyingRate: BigDecimal,
    @ColumnInfo(name = "middle_rate") val middleRate: BigDecimal,
    @ColumnInfo(name = "selling_rate") val sellingRate: BigDecimal
)