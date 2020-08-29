package ru.bulat.currencyconvertertest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import ru.bulat.currencyconvertertest.model.entity.Currency
import ru.bulat.currencyconvertertest.ui.currency.CurrencyViewModel

@RunWith(JUnit4::class)
class CurrencyViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var currencyViewModel: CurrencyViewModel

    @Before
    fun setUp() {
        this.currencyViewModel = CurrencyViewModel()
    }

    @Test
    fun testConverter() {
        val observer = mock(Observer::class.java) as Observer<String>
        this.currencyViewModel.resultLiveData.observeForever(observer)
        val amount = 10f
        val fromCurrency = Currency().apply {
            this.name = "Доллар США"
            this.charCode = "USD"
            this.nominal = 1
            this.value = 74.6382f
            this.numCode = 840
        }
        val toCurrency = Currency().apply {
            this.name = "Венгерских форинтов"
            this.charCode = "HUF"
            this.nominal = 100
            this.value = 24.9359f
            this.numCode = 348
        }
        //10 USD -> 746.38 RUR -> 2993.20 HUF
        currencyViewModel.convertCurrency(amount, fromCurrency, toCurrency)
        assertEquals("2993,20", this.currencyViewModel.resultLiveData.value)
    }
}