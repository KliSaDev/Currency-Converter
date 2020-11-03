package com.example.currencyconverter.data

import android.content.Context
import androidx.preference.PreferenceManager

class CurrencyPreferences(context: Context) {

    companion object {
        const val KEY_ARE_CURRENCIES_SWITCHED = "KEY_ARE_CURRENCIES_SWITCHED"
        const val KEY_SELECTED_CURRENCY_NAME = "KEY_SELECTED_CURRENCY_NAME"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun saveString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getString(key: String): String {
        return preferences.getString(key, "AUD") ?: "AUD"
    }
}