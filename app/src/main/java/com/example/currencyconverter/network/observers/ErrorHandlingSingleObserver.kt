package com.example.currencyconverter.network.observers

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

interface ErrorHandlingSingleObserver<T> : SingleObserver<T> {

    override fun onSubscribe(d: Disposable) {}
    override fun onError(e: Throwable) {}

}