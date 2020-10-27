package com.example.currencyconverter.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import timber.log.Timber

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.showKeyboard() = try {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(
        InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY)
} catch (e: Exception) {
    Timber.e("Can't change keyboard visibility")
}

fun View.hideKeyboard() = try {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
} catch (e: Exception) {
    Timber.e("Can't change keyboard visibility")
}

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layout, this, attachToRoot)

/**
 * This method is meant to be consistently used for all views throughout the app that need to be slightly
 * animated, for example, when their visibility changes, etc.
 */
fun View.setBaseAnimation() {
    this.animation = TranslateAnimation(0f, 0f, 50f, 0f).apply {
        duration = 500L
        interpolator = DecelerateInterpolator()
    }
}