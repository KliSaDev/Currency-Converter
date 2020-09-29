package com.example.currencyconverter.ui.main.selectcurrency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.currencyconverter.R
import com.example.currencyconverter.data.Currency
import com.example.currencyconverter.data.repositories.CurrencyRepository
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.fragment_select_currency_dialog.*
import javax.inject.Inject

class SelectCurrencyDialog : DaggerDialogFragment() {

    companion object {
        const val TAG = "SelectCurrencyDialog"
        const val ARG_SELECTED_CURRENCY = "ARG_SELECTED_CURRENCY"

        @JvmStatic
        fun newInstance(selectedCurrency: Currency): SelectCurrencyDialog =
            SelectCurrencyDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SELECTED_CURRENCY, selectedCurrency)
                }
            }
    }

    @Inject
    lateinit var currencyRepository: CurrencyRepository

    private var selectedCurrency: Currency? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            selectedCurrency = it.getParcelable(ARG_SELECTED_CURRENCY) as Currency?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_currency_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRadioButtons()
        setupButtons()
    }

    private fun setupRadioButtons() {
        val currencies = currencyRepository.getAllCurrencies()
        currencies.forEach { currency ->
            addRadioButton(currency.currencyName)
        }
    }

    private fun addRadioButton(currencyName: String) {
        val rb = RadioButton(requireContext())
        val paddingVertical = resources.getDimensionPixelOffset(R.dimen.spacing_1x)
        rb.apply {
            text = currencyName
            setPadding(rb.paddingStart, paddingVertical, rb.paddingEnd, paddingVertical)
        }
        radioGroup.addView(rb)
    }

    private fun setupButtons() {
        positiveButton.setOnClickListener {
            // TODO get checked radio button from radio group and get text
        }
        negativeButton.setOnClickListener {
            // TODO
        }
    }
}