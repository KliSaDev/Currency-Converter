package com.example.currencyconverter.di

import com.example.currencyconverter.ui.main.MainActivity
import com.example.currencyconverter.ui.main.MainViewModelModule
import com.example.currencyconverter.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [MainViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity
}