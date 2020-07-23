package com.example.currencyconverter.ui.main.convert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.currencyconverter.BaseFragment
import com.example.currencyconverter.R
import com.example.currencyconverter.di.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_convert_currency.*
import javax.inject.Inject

class ConvertCurrencyFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private val viewModel by viewModels<ConvertCurrencyViewModel> { factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_convert_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init()

        viewModel.text.observe(viewLifecycleOwner, Observer {
            text_home.text = it
        })
    }
}