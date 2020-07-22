package com.example.currencyconverter.di

import com.example.currencyconverter.ui.main.convert.ConvertCurrencyFragment
import com.example.currencyconverter.ui.main.currencies.CurrencyListFragment
import com.example.currencyconverter.ui.main.weeklyrates.WeeklyRatesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeConvertCurrencyFragment(): ConvertCurrencyFragment

    @ContributesAndroidInjector
    abstract fun contributeCurrencyListFragment(): CurrencyListFragment

    @ContributesAndroidInjector
    abstract fun contributeWeeklyRatesFragment(): WeeklyRatesFragment
}