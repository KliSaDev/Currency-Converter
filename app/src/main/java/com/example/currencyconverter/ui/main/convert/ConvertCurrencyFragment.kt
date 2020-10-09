package com.example.currencyconverter.ui.main.convert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.currencyconverter.BaseFragment
import com.example.currencyconverter.R
import com.example.currencyconverter.data.models.Currency
import com.example.currencyconverter.ui.main.selectcurrency.SelectCurrencyDialog
import com.example.currencyconverter.util.checkIfFragmentAlreadyOpened
import com.example.currencyconverter.util.hide
import com.example.currencyconverter.util.show
import com.example.currencyconverter.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_convert_currency.*
import kotlinx.android.synthetic.main.layout_no_internet_connection.*

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
                is NoInternetConnection -> showNoInternetConnectionContainer()
            }
        })

        viewModel.init()
        setupCalculateButton()
        setupRetryButton()
    }

    private fun setupLayout(state: ConvertCurrencyState) {
        setupSelectedFromCurrencyButton(state.selectedFromCurrency)
        setupSelectedToCurrencyButton()
        fromCurrencyInput.apply {
            setText(state.fromValue)
            fromCurrencyInput.text?.length?.let { setSelection(it) }
        }
        toCurrencyInput.setText(state.toValue)
        showCurrencyConvertContainer()
    }

    private fun setupSelectedFromCurrencyButton(selectedFromCurrency: Currency) {
        selectedFromCurrencyButton.apply {
            text = selectedFromCurrency.currencyName
            setOnClickListener {
                requireActivity().checkIfFragmentAlreadyOpened(
                    SelectCurrencyDialog.TAG,
                    { showSelectCurrencyDialog(selectedFromCurrency) }
                )
            }
        }
    }

    private fun setupSelectedToCurrencyButton() {
        selectedToCurrencyButton.text = getString(R.string.hrk)
    }

    private fun showSelectCurrencyDialog(selectedFromCurrency: Currency) {
        SelectCurrencyDialog.newInstance(selectedFromCurrency).apply {
            onConfirmListener = { newSelectedCurrencyName ->
                viewModel.onNewCurrencySelected(newSelectedCurrencyName)
            }
        }.show(requireActivity().supportFragmentManager, SelectCurrencyDialog.TAG)
    }


    private fun setupCalculateButton() {
        buttonCalculate.setOnClickListener {
            viewModel.onCalculateClicked(fromCurrencyInput.text.toString())
        }
    }

    private fun setupRetryButton() {
        buttonRetry.setOnClickListener {
            viewModel.init()
        }
    }

    private fun showCurrencyConvertContainer() {
        noInternetConnectionContainer.hide()
        convertCurrencyContainer.show()
        shouldEnableBottomNavigationView(true)
    }

    private fun showNoInternetConnectionContainer() {
        convertCurrencyContainer.hide()
        noInternetConnectionContainer.show()
        shouldEnableBottomNavigationView(false)
    }

    private fun shouldEnableBottomNavigationView(shouldEnable: Boolean) {
        requireActivity().bottomNavigationView.menu.forEach { it.isEnabled = shouldEnable }
    }
}