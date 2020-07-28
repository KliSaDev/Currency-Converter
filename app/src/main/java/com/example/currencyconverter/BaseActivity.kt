package com.example.currencyconverter

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.di.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    inline fun <reified T : ViewModel> getViewModel(viewModel: T): T {
        val model by viewModels<T>() {factory}
        return model
    }
}