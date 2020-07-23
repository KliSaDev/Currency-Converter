package com.example.currencyconverter.di

import com.example.currencyconverter.ui.main.convert.ConvertCurrencyFragment
import com.example.currencyconverter.ui.main.convert.ConvertCurrencyViewModelModule
import com.example.currencyconverter.ui.main.currencies.CurrencyListFragment
import com.example.currencyconverter.ui.main.currencies.CurrencyListViewModelModule
import com.example.currencyconverter.ui.main.weeklyrates.WeeklyRatesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [(ConvertCurrencyViewModelModule::class)])
    abstract fun contributeConvertCurrencyFragment(): ConvertCurrencyFragment

    @ContributesAndroidInjector(modules = [(CurrencyListViewModelModule::class)])
    abstract fun contributeCurrencyListFragment(): CurrencyListFragment

    @ContributesAndroidInjector
    abstract fun contributeWeeklyRatesFragment(): WeeklyRatesFragment
}