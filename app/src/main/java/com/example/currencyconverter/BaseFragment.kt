package com.example.currencyconverter

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.currencyconverter.di.ViewModelProviderFactory
import com.example.currencyconverter.util.hide
import com.example.currencyconverter.util.show
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

abstract class BaseFragment<State : Any, Event : Any> : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory

    protected abstract val viewModel: BaseViewModel<State, Event>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.commonStateData().observe(viewLifecycleOwner, Observer { progressState ->
            when (progressState) {
                ProgressState.IDLE -> requireActivity().progressBarContainer.hide()
                ProgressState.IS_SHOWING -> requireActivity().progressBarContainer.show()
                else -> progressBarContainer.hide()
            }
        })
    }
}