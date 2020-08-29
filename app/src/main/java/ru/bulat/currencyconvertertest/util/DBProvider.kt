package ru.bulat.currencyconvertertest.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import ru.bulat.currencyconvertertest.model.entity.Currency


object DBProvider {
    lateinit var context: Context
    private lateinit var db: SQLiteDatabase

    private fun openDb() {
        if (!::db.isInitialized || !db.isOpen)
            db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null)
    }

    private fun closeDb() {
        if (db.isOpen)
            db.close()
    }

    private fun createTable() {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS currency (" +
                    "numCode INTEGER," +
                    "charCode TEXT," +
                    "nominal INTEGER," +
                    "name TEXT," +
                    "value REAL)"
        )
    }

    private fun generateInsertSql(currencies: List<Currency>): String {
        var values = ""
        for ((index, currency) in currencies.withIndex()) {
            values += "(${currency.numCode}, '${currency.charCode}', ${currency.nominal}, '${currency.name}', ${currency.value})"
            if (index < currencies.size - 1)
                values += ","
        }
        return "INSERT INTO currency (numCode, charCode, nominal, name, value) VALUES $values;"
    }

    fun updateCurrencies(currencies: List<Currency>) {
        openDb()
        createTable()
        db.execSQL("DELETE FROM currency;")
        db.execSQL(generateInsertSql(currencies))

        closeDb()
    }

    fun getCurrencies(): List<Currency> {
        openDb()
        createTable()

        val query = db.rawQuery("SELECT * FROM currency;", null)
        val currencies = ArrayList<Currency>()
        if (query.moveToFirst()) {
            do {
                val numCode = query.getInt(0)
                val charCode = query.getString(1)
                val nominal = query.getInt(2)
                val name = query.getString(3)
                val value = query.getFloat(4)
                currencies.add(Currency().apply {
                    this.numCode = numCode
                    this.charCode = charCode
                    this.nominal = nominal
                    this.name = name
                    this.value = value
                })
            } while (query.moveToNext())
        }
        query.close()

        closeDb()
        return currencies
    }
}