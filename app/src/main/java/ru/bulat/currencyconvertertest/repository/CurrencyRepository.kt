package ru.bulat.currencyconvertertest.repository

import kotlinx.coroutines.*
import org.simpleframework.xml.core.Persister
import ru.bulat.currencyconvertertest.model.entity.Currency
import ru.bulat.currencyconvertertest.model.xml.ValCurs
import ru.bulat.currencyconvertertest.util.Constants.BASE_URL
import ru.bulat.currencyconvertertest.util.CurrencyMapper
import ru.bulat.currencyconvertertest.util.DBProvider
import ru.bulat.currencyconvertertest.util.Result
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext


class CurrencyRepository {
    private val url = URL(BASE_URL)

    suspend fun getCurrencyList(callbackResponse: (response: Result<List<Currency>>) -> Unit) = withContext(Dispatchers.IO) {
        val result = Result<List<Currency>>()
        var currencies = DBProvider.getCurrencies()
        result.response = currencies
        callbackResponse.invoke(result)
        try {
            with(url.openConnection() as HttpURLConnection) {
                BufferedReader(InputStreamReader(inputStream, "Windows-1251")).use { bufferedReader ->
                    val response = StringBuffer()

                    var inputLine = bufferedReader.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = bufferedReader.readLine()
                    }
                    bufferedReader.close()

                    val serializer = Persister()
                    try {
                        val valCurs = serializer.read(ValCurs::class.java, response.toString())
                        currencies = valCurs.valuties.map {
                            CurrencyMapper.convert(it)
                        }
                        DBProvider.updateCurrencies(currencies)
                        result.response = currencies
                    } catch (exception: Exception) {
                        result.exception = exception
                        exception.printStackTrace()
                    }

                    callbackResponse.invoke(result)
                }
            }
        } catch (exception: UnknownHostException) {
            result.exception = exception
            callbackResponse.invoke(result)
        }
    }
}