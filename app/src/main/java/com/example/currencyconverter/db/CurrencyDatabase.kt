package com.example.currencyconverter.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.currencyconverter.network.adapters.BigDecimalAdapter
import com.example.currencyconverter.network.adapters.LocalDateAdapter

@Database(entities = [CurrencyEntity::class], version = 1)
@TypeConverters(LocalDateAdapter::class, BigDecimalAdapter::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}