package com.example.currencyconverter.ui.main.weeklyrates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.currencyconverter.R

class WeeklyRatesFragment : Fragment() {

    private lateinit var weeklyRatesViewModel: WeeklyRatesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weeklyRatesViewModel =
            ViewModelProviders.of(this).get(WeeklyRatesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_weekly_rates, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        weeklyRatesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}