package com.example.currencyconverter.di

import android.content.Context
import com.example.currencyconverter.CurrencyConverterApplication
import com.example.currencyconverter.di.network.InteractorsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class,
        ApiModule::class,
        AppModule::class,
        InteractorsModule::class,
        ViewModelProviderFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<CurrencyConverterApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}