package com.example.currencyconverter.ui.main.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.currencyconverter.R

class CurrencyListFragment : Fragment() {

    private lateinit var currencyListViewModel: CurrencyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currencyListViewModel =
            ViewModelProviders.of(this).get(CurrencyListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_currency_list, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        currencyListViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}