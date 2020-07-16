package com.example.currencyconverter.di

import com.example.currencyconverter.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Timber.tag("OkHttp").i(it)
            }).apply { level = HttpLoggingInterceptor.Level.BODY })
        }

        return okHttpClient.build()
    }
}