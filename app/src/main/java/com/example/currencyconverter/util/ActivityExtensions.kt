package com.example.currencyconverter.util

import androidx.fragment.app.FragmentActivity

inline fun FragmentActivity.checkIfFragmentAlreadyOpened(tag: String, listener: () -> Unit) {
    if (this.supportFragmentManager.findFragmentByTag(tag) == null) listener()
}