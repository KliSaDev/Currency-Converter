package com.example.currencyconverter.network.observers

import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable

interface ErrorHandlingCompletableObserver : CompletableObserver {

    override fun onSubscribe(d: Disposable) {}

    override fun onError(e: Throwable) {}

}