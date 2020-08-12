package com.example.currencyconverter.data.repositories

import com.example.currencyconverter.db.CurrencyDatabase
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyDatabase: CurrencyDatabase
) {


}