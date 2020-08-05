package com.example.currencyconverter.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.currencyconverter.BaseActivity
import com.example.currencyconverter.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val viewModel by viewModels<MainViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.init()
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_currency_converter,
                R.id.navigation_currencies_list,
                R.id.navigation_weekly_rates
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.apply {
            setupWithNavController(navController)
            setOnNavigationItemReselectedListener {
                // Do nothing. We don't want to recreate Fragment if user clicks on already
                // selected menu item.
            }
        }
    }
}