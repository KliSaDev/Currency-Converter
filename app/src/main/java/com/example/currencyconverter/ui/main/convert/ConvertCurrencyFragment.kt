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
import com.example.currencyconverter.util.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_convert_currency.*


class ConvertCurrencyFragment : BaseFragment<ConvertCurrencyState, ConvertCurrencyEvent>() {

    override val viewModel by viewModels<ConvertCurrencyViewModel> { factory }

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

    private fun showCurrencyConvertContainer() {
        wifiOffImage.hide()
        convertCurrencyContainer.show()
        shouldEnableBottomNavigationView(true)
    }

    private fun showNoInternetConnectionContainer() {
        convertCurrencyContainer.hide()
        wifiOffImage.show()

        Snackbar.make(
            convertCurrencyContainer,
            getString(R.string.no_internet_connection_title),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(getString(R.string.retry_button)) { viewModel.init() }
            .setActionTextColor(requireContext().getCompatColor(R.color.colorSecondary))
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .show()

        shouldEnableBottomNavigationView(false)
    }

    private fun shouldEnableBottomNavigationView(shouldEnable: Boolean) {
        requireActivity().bottomNavigationView.menu.forEach { it.isEnabled = shouldEnable }
    }
}