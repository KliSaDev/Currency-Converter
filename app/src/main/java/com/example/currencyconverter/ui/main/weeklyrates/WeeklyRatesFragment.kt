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
import com.example.currencyconverter.data.models.DailyCurrencyValue
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.fragment_weekly_rates.*
import java.math.RoundingMode

class WeeklyRatesFragment : BaseFragment() {

    private val viewModel by viewModels<WeeklyRatesViewModel> { factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weekly_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewStateData().observe(viewLifecycleOwner, Observer { state ->
            setupChartData(state.dailyValues)
        })

        viewModel.init()
        setupChart()
        setupXAxis()

        val yAxisRight = weeklyRatesChart.axisRight
        yAxisRight.isEnabled = false
    }

    private fun setupChart() {
        weeklyRatesChart.setBorderWidth(3.0f)
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
        weeklyRatesChart.invalidate()
    }

    private fun setupChartData(dailyValues: List<DailyCurrencyValue>) {
        val entries = mutableListOf<Entry>()
        val xAxisLabels = mutableListOf<String>()

        dailyValues.forEachIndexed { index, dailyValue ->
            val rateValue = dailyValue.middleRate.setScale(3, RoundingMode.DOWN).toFloat()
            entries.add(Entry(index.toFloat(), rateValue))
            xAxisLabels.add(dailyValue.date.toString())
        }

        weeklyRatesChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return xAxisLabels[value.toInt()]
            }
        }

        val dataSet = LineDataSet(entries, "")
        dataSet.lineWidth = 3f
        dataSet.circleRadius = 3f
        dataSet.circleHoleColor = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        dataSet.circleColors = mutableListOf(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
        weeklyRatesChart.data = LineData(dataSet)
    }

    private fun setupXAxis() {
        val xAxis = weeklyRatesChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.spaceMax = 0.3f
        xAxis.spaceMin = 0.3f
    }
}