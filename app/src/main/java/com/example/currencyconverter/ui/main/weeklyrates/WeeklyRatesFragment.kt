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
import com.example.currencyconverter.util.checkIfFragmentAlreadyOpened
import com.example.currencyconverter.util.getCompatColor
import com.example.currencyconverter.util.getDay
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.fragment_weekly_rates.*
import kotlinx.android.synthetic.main.fragment_weekly_rates.selectedFromCurrencyButton
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
            setupSelectedFromCurrencyButton(state.selectedFromCurrency)
        })

        viewModel.init()
        setupChart()
        setupXAxis()
    }

    private fun setupChart() {
        weeklyRatesChart.apply {
            setBorderWidth(3.0f)
            isHighlightPerTapEnabled = false
            setDrawBorders(true)
            description.isEnabled = false
            setScaleEnabled(false)
            legend.isEnabled = false
            setDrawGridBackground(false)
            setBorderColor(requireContext().getCompatColor(R.color.colorPrimary))
            invalidate()
        }
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
        dataSet.apply {
            lineWidth = 3f
            circleRadius = 5f
            circleHoleColor = requireContext().getCompatColor(R.color.colorAccent)
            circleColors = mutableListOf(requireContext().getCompatColor(R.color.colorAccent))
            color = requireContext().getCompatColor(R.color.colorAccent)
        }
        weeklyRatesChart.apply {
            data = LineData(dataSet)
            notifyDataSetChanged()
            invalidate()
        }

        val yAxisRight = weeklyRatesChart.axisRight
        yAxisRight.isEnabled = false
        val yAxisLeft = weeklyRatesChart.axisLeft
        yAxisLeft.gridColor = requireContext().getCompatColor(R.color.colorPrimary_40Alpha)
//        yAxisLeft.axisMinimum = 4f
//        yAxisLeft.axisMaximum = 5f // TODO set of selected currency
    }

    private fun setupXAxis() {
        val xAxis = weeklyRatesChart.xAxis
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
            granularity = 1f
            spaceMax = 0.3f
            spaceMin = 0.3f
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