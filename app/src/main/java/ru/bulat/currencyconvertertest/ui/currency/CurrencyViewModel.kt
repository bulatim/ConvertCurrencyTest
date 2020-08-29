package ru.bulat.currencyconvertertest.ui.currency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.bulat.currencyconvertertest.model.entity.Currency
import ru.bulat.currencyconvertertest.repository.CurrencyRepository
import java.net.UnknownHostException

class CurrencyViewModel : ViewModel() {
    private val currencyRepository = CurrencyRepository()
    val currencyListMutableLiveData = MutableLiveData<List<Currency>>()
    val resultLiveData = MutableLiveData<String>()
    val mistakeLiveData = MutableLiveData<String>()

    fun getCurrencyList() {
        viewModelScope.launch {
            currencyRepository.getCurrencyList { result ->
                if (result.isSuccess) {
                    currencyListMutableLiveData.postValue(result.response)
                } else {
                    if (result.exception is UnknownHostException) {
                        mistakeLiveData.postValue("Данные не обновлены, отсутствует доступ в сеть")
                    }
                }
            }
        }
    }

    fun convertCurrency(
        convertAmount: Float,
        currentFromCurrency: Currency,
        currentToCurrency: Currency
    ) {
        if (currentFromCurrency.nominal != null && currentFromCurrency.value != null)
            resultLiveData.value =
                convert(convertAmount, currentFromCurrency, currentToCurrency)
    }

    fun convert(
        convertAmount: Float,
        currentFromCurrency: Currency,
        currentToCurrency: Currency
    ): String {
        val rur = (convertAmount * currentFromCurrency.value!!) / currentFromCurrency.nominal!!
        val result = (rur * currentToCurrency.nominal!!) / currentToCurrency.value!!
        return String.format(
            "%.2f", result
        )
    }
}