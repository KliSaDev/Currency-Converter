package com.example.currencyconverter.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}