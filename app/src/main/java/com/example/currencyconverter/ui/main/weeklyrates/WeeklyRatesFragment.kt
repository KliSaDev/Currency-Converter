package com.example.currencyconverter.ui.main.weeklyrates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.currencyconverter.BaseFragment
import com.example.currencyconverter.R
import com.example.currencyconverter.data.models.Currency
import com.example.currencyconverter.data.models.DailyCurrencyValue
import com.example.currencyconverter.ui.main.selectcurrency.SelectCurrencyDialog
import com.example.currencyconverter.util.*
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.fragment_weekly_rates.*
import kotlinx.android.synthetic.main.fragment_weekly_rates.selectedFromCurrencyButton

class WeeklyRatesFragment : BaseFragment<WeeklyRatesState, WeeklyRatesEvent>() {

    override val viewModel by viewModels<WeeklyRatesViewModel> { factory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weekly_rates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewStateData().observe(viewLifecycleOwner, Observer { state ->
            setupChartData(state.dailyValues)
            setupSelectedFromCurrencyButton(state.selectedFromCurrency)
            val middleRate = state.selectedFromCurrency.middleRate.toFloat()
            setupYAxis(
                minValue = middleRate - MIN_AND_MAX_VALUE_OFFSET_FOR_RATE,
                maxValue = middleRate + MIN_AND_MAX_VALUE_OFFSET_FOR_RATE
            )
        })

        viewModel.init()
        setupChart()
        setupXAxis()
    }

    private fun setupChart() {
        weeklyRatesChart.apply {
            setBorderWidth(BORDER_WIDTH)
            isHighlightPerTapEnabled = false
            setDrawBorders(true)
            description.isEnabled = false
            setScaleEnabled(false)
            legend.isEnabled = false
            setDrawGridBackground(false)
            setBorderColor(requireContext().getCompatColor(R.color.colorPrimary))
            extraBottomOffset = 8f
            invalidate()
        }
    }

    private fun setupChartData(dailyValues: List<DailyCurrencyValue>) {
        val entries = mutableListOf<Entry>()
        val xAxisLabels = mutableListOf<String>()

        dailyValues.forEachIndexed { index, dailyValue ->
            entries.add(Entry(index.toFloat(), dailyValue.middleRate.toFloat()))
            xAxisLabels.add(dailyValue.date.getDay())
        }

        weeklyRatesChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return xAxisLabels[value.toInt()]
            }
        }

        val dataSet = LineDataSet(entries, "")
        dataSet.apply {
            lineWidth = LINE_WIDTH
            circleRadius = CIRCLE_RADIUS
            circleHoleColor = requireContext().getCompatColor(R.color.colorAccent)
            circleColors = mutableListOf(requireContext().getCompatColor(R.color.colorAccent))
            color = requireContext().getCompatColor(R.color.colorAccent)
            valueTextSize = VALUE_TEXT_SIZE
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return String.format(FORMAT_RATE_VALUE_IN_CHART, value)
                }
            }
        }
        weeklyRatesChart.apply {
            data = LineData(dataSet)
            notifyDataSetChanged()
            invalidate()
        }
    }

    private fun setupYAxis(minValue: Float, maxValue: Float) {
        val yAxisRight = weeklyRatesChart.axisRight
        yAxisRight.isEnabled = false
        val yAxisLeft = weeklyRatesChart.axisLeft
        yAxisLeft.apply {
            gridColor = requireContext().getCompatColor(R.color.colorPrimary_40Alpha)
            axisMinimum = minValue
            axisMaximum = maxValue
            textSize = Y_AXIS_LABEL_TEXT_SIZE
        }
    }

    private fun setupXAxis() {
        val xAxis = weeklyRatesChart.xAxis
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            granularity = X_AXIS_GRANULARITY
            spaceMax = X_AXIS_OFFSET_FOR_VALUES
            spaceMin = X_AXIS_OFFSET_FOR_VALUES
            textSize = X_AXIS_LABEL_TEXT_SIZE
        }
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

    private fun showSelectCurrencyDialog(selectedFromCurrency: Currency) {
        SelectCurrencyDialog.newInstance(selectedFromCurrency).apply {
            onConfirmListener = { newSelectedCurrencyName ->
                viewModel.onNewCurrencySelected(newSelectedCurrencyName)
            }
        }.show(requireActivity().supportFragmentManager, SelectCurrencyDialog.TAG)
    }
}