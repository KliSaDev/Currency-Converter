package com.example.currencyconverter.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrency(vararg currencies: CurrencyEntity): Completable

    @Update
    fun updateCurrency(currency: CurrencyEntity): Completable

    @Query("SELECT * FROM currency_entity WHERE id LIKE :id")
    fun getCurrencyById(id: String): Single<CurrencyEntity>

    @Query("SELECT * FROM currency_entity ORDER BY currency_name")
    fun getAllCurrencies(): Single<List<CurrencyEntity>>
}