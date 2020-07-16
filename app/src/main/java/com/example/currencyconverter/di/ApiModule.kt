package com.example.currencyconverter.di

import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.network.ApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
abstract class ApiModule {

    companion object {

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

        @Provides
        @Singleton
        fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)
    }
}