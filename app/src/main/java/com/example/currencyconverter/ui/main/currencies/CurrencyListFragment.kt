package com.example.currencyconverter.ui.main.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.BaseFragment
import com.example.currencyconverter.R
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.ui.adapters.CurrencyListAdapter
import kotlinx.android.synthetic.main.fragment_currency_list.*

class CurrencyListFragment : BaseFragment() {

    private val viewModel by viewModels<CurrencyListViewModel> { factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewStateData().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is CurrencyListState -> showCurrencies(state.currencies)
            }
        })

        viewModel.init()
        viewModel.text.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun showCurrencies(currencies: List<Currency>) {
        val currenciesAdapter = CurrencyListAdapter()
        currenciesAdapter.items = currencies
        currenciesListRecyclerView.layoutManager = LinearLayoutManager(context)
        currenciesListRecyclerView.adapter = currenciesAdapter
    }
}