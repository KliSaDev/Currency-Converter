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
    private val commonStateLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun viewStateData(): LiveData<State> = stateLiveData
    fun viewEventData(): LiveData<Event> = eventLiveData
    fun commonStateData(): LiveData<ProgressState> = commonStateLiveData

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

    protected fun showProgress() {
        commonStateLiveData.value = ProgressState.IS_SHOWING
    }

    protected fun hideProgress() {
        commonStateLiveData.value = ProgressState.IDLE
    }
}

enum class ProgressState {
    IS_SHOWING,
    IDLE
}