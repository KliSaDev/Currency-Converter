package com.example.currencyconverter.ui.main.weeklyrates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.currencyconverter.BaseFragment
import com.example.currencyconverter.R
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.fragment_weekly_rates.*

class WeeklyRatesFragment : BaseFragment() {

    private val viewModel by viewModels<WeeklyRatesViewModel> { factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weekly_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init()
        viewModel.text.observe(viewLifecycleOwner, Observer {
            text_notifications.text = it
        })

        setupChart()

        val entries = listOf(
            BarEntry(1f, 4.22f),
            BarEntry(2f, 5.66f),
            BarEntry(3f, 2.3f),
            BarEntry(4f, 5.66f)
        )

        val lineDataSet1 = BarDataSet(entries, "AUD")
        lineDataSet1.color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        weeklyRatesChart.data = BarData(lineDataSet1)
//        weeklyRatesChart.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))


        val xAxis = weeklyRatesChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "String"
            }
        }

        val yAxisRight = weeklyRatesChart.axisRight
        yAxisRight.isEnabled = false
    }

    private fun setupChart() {
        weeklyRatesChart.setBorderWidth(4.0f)
        weeklyRatesChart.isHighlightPerTapEnabled = false
        weeklyRatesChart.setDrawBorders(true)
        weeklyRatesChart.description.isEnabled = false
        weeklyRatesChart.setScaleEnabled(false)
        weeklyRatesChart.legend.isEnabled = false
        weeklyRatesChart.setDrawGridBackground(false)
        weeklyRatesChart.setBorderColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorPrimary
            )
        )
        weeklyRatesChart.setFitBars(true)
        weeklyRatesChart.invalidate()
    }
}