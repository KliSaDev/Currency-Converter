package com.example.currencyconverter

import com.example.currencyconverter.di.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory
}