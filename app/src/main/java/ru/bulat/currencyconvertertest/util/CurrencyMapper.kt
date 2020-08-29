package ru.bulat.currencyconvertertest.util

import ru.bulat.currencyconvertertest.model.entity.Currency
import ru.bulat.currencyconvertertest.model.xml.Valute

object CurrencyMapper {
    fun convert(valute: Valute) = Currency().apply {
        this.charCode = valute.charCode
        this.name = valute.name
        this.nominal = valute.nominal
        this.numCode = valute.numCode
        this.value = valute.value?.replace(",",".")?.toFloat()
    }
}