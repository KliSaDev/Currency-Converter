package com.example.currencyconverter.ui.main.convert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.currencyconverter.R

class ConvertCurrencyFragment : Fragment() {

    private lateinit var convertCurrencyViewModel: ConvertCurrencyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        convertCurrencyViewModel =
            ViewModelProviders.of(this).get(ConvertCurrencyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_convert_currency, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        convertCurrencyViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}