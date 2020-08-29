package ru.bulat.currencyconvertertest.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.currency_fragment.*
import ru.bulat.currencyconvertertest.R
import ru.bulat.currencyconvertertest.model.entity.Currency
import java.lang.NumberFormatException

class CurrencyFragment : Fragment() {
    lateinit var viewModel: CurrencyViewModel
    var currentFromCurrency: Currency? = null
    var currentToCurrency: Currency? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.currency_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fromCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentFromCurrency = fromCurrency.adapter.getItem(position) as Currency
            }
        }
        toCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentToCurrency = toCurrency.adapter.getItem(position) as Currency
            }
        }
        convert.setOnClickListener {
            if (amount.text.toString() != "") {
                try {
                    val convertAmount = amount.text.toString().toFloat()
                    if (currentFromCurrency != null && currentToCurrency != null)
                        viewModel.convertCurrency(
                            convertAmount,
                            currentFromCurrency!!,
                            currentToCurrency!!
                        )
                } catch (e: NumberFormatException) {
                    showToast("Введите корректное значение")
                }
            } else
                showToast("Значение не может быть пустым")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)

        viewModel.currencyListMutableLiveData.observe(this, Observer { currencies ->
            val adapter =
                CurrencyAdapter(context!!, currencies)
            currentFromCurrency = currencies[0]
            currentToCurrency = currencies[0]
            fromCurrency.adapter = adapter
            toCurrency.adapter = adapter
        })

        viewModel.resultLiveData.observe(this, Observer {
            result.text = it
        })

        viewModel.mistakeLiveData.observe(this, Observer {
            showToast(it)
        })
        viewModel.getCurrencyList()
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}