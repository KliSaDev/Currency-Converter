package com.example.currencyconverter.network

interface Interactor<Input, Output> {

    fun execute(input: Input): Output
}