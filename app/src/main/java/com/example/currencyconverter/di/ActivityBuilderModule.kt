package com.example.currencyconverter.di

import com.example.currencyconverter.ui.main.MainActivity
import com.example.currencyconverter.ui.main.MainViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainViewModelModule::class])
    abstract fun contributeMainActivity(): MainActivity
}