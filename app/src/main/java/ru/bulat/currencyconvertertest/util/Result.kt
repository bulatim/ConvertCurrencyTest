package ru.bulat.currencyconvertertest.util

class Result<T> {
    var exception: Throwable? = null
    var response: T? = null

    val isSuccess: Boolean
        get() = exception == null
}