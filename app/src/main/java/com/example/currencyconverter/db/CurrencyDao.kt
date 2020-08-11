package com.example.currencyconverter.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface CurrencyDao {

    @Insert
    fun insertCurrency(vararg currencies: CurrencyEntity)

    @Query("SELECT * FROM currency_entity")
    fun getAllCurrencies(): Single<List<CurrencyEntity>>
}