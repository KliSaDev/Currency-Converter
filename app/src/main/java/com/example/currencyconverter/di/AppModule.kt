package com.example.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.data.repositories.CurrencyRepository
import com.example.currencyconverter.db.CurrencyDatabase
import com.example.currencyconverter.util.CURRENCY_DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {

    companion object {

        @Provides
        @Singleton
        fun provideCurrencyDatabase(context: Context): CurrencyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                CurrencyDatabase::class.java,
                CURRENCY_DATABASE_NAME
            ).build()
        }

        @Provides
        @Singleton
        fun provideCurrencyRepository(db: CurrencyDatabase) = CurrencyRepository(db)
    }
}