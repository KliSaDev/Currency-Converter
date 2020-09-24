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

    @Query("SELECT * FROM currency_entity WHERE id LIKE :id")
    fun getCurrencyById(id: String): Single<CurrencyEntity>

    @Query("SELECT * FROM currency_entity LIMIT 1")
    fun getTopmostCurrency(): Single<CurrencyEntity>

    @Query("SELECT * FROM currency_entity")
    fun getAllCurrencies(): Single<List<CurrencyEntity>>
}