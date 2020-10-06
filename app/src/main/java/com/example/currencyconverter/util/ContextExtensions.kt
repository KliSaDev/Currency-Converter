package com.example.currencyconverter.util

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.getCompatColor(colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}