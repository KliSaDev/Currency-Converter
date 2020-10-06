package com.example.currencyconverter.ui.main.weeklyrates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.currencyconverter.BaseFragment
import com.example.currencyconverter.R
import com.example.currencyconverter.data.models.DailyCurrencyValue
import com.example.currencyconverter.util.getCompatColor
import com.example.currencyconverter.util.getDay
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
    }

    private fun setupChart() {
        weeklyRatesChart.setBorderWidth(3.0f)
        weeklyRatesChart.isHighlightPerTapEnabled = false
        weeklyRatesChart.setDrawBorders(true)
        weeklyRatesChart.description.isEnabled = false
        weeklyRatesChart.setScaleEnabled(false)
        weeklyRatesChart.legend.isEnabled = false
        weeklyRatesChart.setDrawGridBackground(false)
        weeklyRatesChart.setBorderColor(requireContext().getCompatColor(R.color.colorPrimary))
        weeklyRatesChart.invalidate()
    }

    private fun setupChartData(dailyValues: List<DailyCurrencyValue>) {
        val entries = mutableListOf<Entry>()
        val xAxisLabels = mutableListOf<String>()

        dailyValues.forEachIndexed { index, dailyValue ->
            val rateValue = dailyValue.middleRate.setScale(3, RoundingMode.DOWN).toFloat()
            entries.add(Entry(index.toFloat(), rateValue))
            xAxisLabels.add(dailyValue.date.getDay())
        }

        weeklyRatesChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return xAxisLabels[value.toInt()]
            }
        }

        val dataSet = LineDataSet(entries, "")
        dataSet.lineWidth = 3f
        dataSet.circleRadius = 5f
        dataSet.circleHoleColor = requireContext().getCompatColor(R.color.colorAccent)
        dataSet.circleColors = mutableListOf(requireContext().getCompatColor(R.color.colorAccent))
        dataSet.color = requireContext().getCompatColor(R.color.colorAccent)
        weeklyRatesChart.data = LineData(dataSet)

        val yAxisRight = weeklyRatesChart.axisRight
        yAxisRight.isEnabled = false
        val yAxisLeft = weeklyRatesChart.axisLeft
        yAxisLeft.gridColor = requireContext().getCompatColor(R.color.colorPrimary_40Alpha)
//        yAxisLeft.axisMinimum = 4f
//        yAxisLeft.axisMaximum = 5f // TODO set of selected currency
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