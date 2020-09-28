package com.example.currencyconverter.ui.main.selectcurrency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.currencyconverter.R
import com.example.currencyconverter.data.Currency

class SelectCurrencyDialog : DialogFragment() {

    companion object {
        const val TAG = "SelectCurrencyDialog"
        const val ARG_SELECTED_CURRENCY = "ARG_SELECTED_CURRENCY"

        @JvmStatic
        fun newInstance(selectedCurrency: Currency) : SelectCurrencyDialog =
            SelectCurrencyDialog()
                .apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SELECTED_CURRENCY, selectedCurrency)
                }
            }
    }

    private var selectedCurrency : Currency? = null

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

    }
}