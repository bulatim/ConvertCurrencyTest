package ru.bulat.currencyconvertertest.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.bulat.currencyconvertertest.R
import ru.bulat.currencyconvertertest.ui.currency.CurrencyFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, CurrencyFragment(), CurrencyFragment::class.java.simpleName)
            .commit()
    }
}
