package com.example.currencyconverter.ui.main.convert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.currencyconverter.BaseFragment
import com.example.currencyconverter.R
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.util.toast
import kotlinx.android.synthetic.main.fragment_convert_currency.*

class ConvertCurrencyFragment : BaseFragment() {

    private val viewModel by viewModels<ConvertCurrencyViewModel> { factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_convert_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewStateData().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is ConvertCurrencyState -> setupLayout(state)
            }
        })

        viewModel.viewEventData().observe(viewLifecycleOwner, Observer { event ->
            when (event) {
                is InvalidNumberInput -> context?.toast(getString(R.string.invalid_number_input))
            }
        })

        viewModel.init()
        setupCalculateButton()
    }

    private fun setupLayout(state: ConvertCurrencyState) {
        setupSelectedFromCurrencyButton(state.selectedFromCurrency)
        fromCurrencyInput.apply {
            setText(state.fromValue)
            fromCurrencyInput.text?.length?.let { setSelection(it) }
        }
        toCurrencyInput.setText(state.toValue)
    }

    private fun setupSelectedFromCurrencyButton(selectedFromCurrency: Currency) {
        selectedFromCurrencyButton.apply {
            text = selectedFromCurrency.currencyName
            setOnClickListener { showChangeCurrencyAlertDialog() }
        }
    }

    private fun showChangeCurrencyAlertDialog() {
        // TODO: implement
    }


    private fun setupCalculateButton() {
        buttonCalculate.setOnClickListener {
            viewModel.onCalculateClicked(fromCurrencyInput.text.toString())
        }
    }
}