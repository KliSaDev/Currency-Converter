package com.example.currencyconverter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.util.LiveEvent
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel<State : Any, Event : Any> : ViewModel() {

    private val stateLiveData: MutableLiveData<State> = MutableLiveData()
    private val eventLiveData: LiveEvent<Event> = LiveEvent()

    fun viewStateData(): LiveData<State> = stateLiveData
    fun viewEventData(): LiveData<Event> = eventLiveData

    protected var viewState: State? = null
        get() = stateLiveData.value ?: field
        set(value) {
            stateLiveData.value = value
        }

    protected fun emitEvent(event: Event) {
        eventLiveData.value = event
    }

    protected fun handleException(throwable: Throwable, event: Event) {
        Timber.e(throwable)
        when (throwable) {
            is UnknownHostException, is SocketTimeoutException, is ConnectException ->
                emitEvent(event)
        }
    }
}