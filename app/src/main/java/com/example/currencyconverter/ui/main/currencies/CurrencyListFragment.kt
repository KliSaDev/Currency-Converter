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
import com.example.currencyconverter.util.show
import kotlinx.android.synthetic.main.fragment_currency_list.*
import javax.inject.Inject

class CurrencyListFragment : BaseFragment() {

    @Inject
    lateinit var currencyAdapter: CurrencyListAdapter

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
    }

    private fun showCurrencies(currencies: List<Currency>) {
        setupAdapter(currencies)
        currenciesListRecyclerView.show()
    }

    private fun setupAdapter(currencies: List<Currency>) {
        currencyAdapter.items = currencies
        currenciesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currencyAdapter
        }
    }
}