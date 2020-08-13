package com.example.currencyconverter.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrency(vararg currencies: CurrencyEntity): Completable

    @Query("SELECT * FROM currency_entity")
    fun getAllCurrencies(): Single<List<CurrencyEntity>>
}