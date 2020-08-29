package ru.bulat.currencyconvertertest

import android.app.Application
import ru.bulat.currencyconvertertest.util.DBProvider

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        DBProvider.context = applicationContext
    }
}