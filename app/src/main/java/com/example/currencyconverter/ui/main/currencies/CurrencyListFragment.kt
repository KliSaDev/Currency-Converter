package com.example.currencyconverter.ui.main.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.BaseFragment
import com.example.currencyconverter.R
import com.example.currencyconverter.data.models.Currency
import com.example.currencyconverter.ui.adapters.CurrencyListAdapter
import com.example.currencyconverter.util.setBaseAnimation
import com.example.currencyconverter.util.show
import kotlinx.android.synthetic.main.fragment_currency_list.*
import javax.inject.Inject

class CurrencyListFragment : BaseFragment<CurrencyListState, CurrencyListEvent>() {

    @Inject
    lateinit var currencyAdapter: CurrencyListAdapter

    override val viewModel by viewModels<CurrencyListViewModel> { factory }

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
        currenciesListRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = currencyAdapter
            setBaseAnimation()
        }
    }
}