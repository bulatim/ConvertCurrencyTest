package ru.bulat.currencyconvertertest.ui.currency

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ru.bulat.currencyconvertertest.model.entity.Currency


class CurrencyAdapter(context: Context, private val currencies: List<Currency>): BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        view.findViewById<TextView>(android.R.id.text1).text = currencies[position].name
        return view
    }

    override fun getItem(position: Int): Any {
        return currencies[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return currencies.size
    }
}