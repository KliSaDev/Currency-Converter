package com.example.currencyconverter.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.data.models.Currency
import com.example.currencyconverter.util.FORMAT_CURRENCY_LIST_RATES
import com.example.currencyconverter.util.inflate
import kotlinx.android.synthetic.main.list_item_currency.view.*


class CurrencyListAdapter : RecyclerView.Adapter<CurrencyListAdapter.CurrencyListViewHolder>() {

    var items: List<Currency> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: ((Currency) -> Unit)? = null

    class CurrencyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(currency: Currency, itemClickListener: ((Currency) -> Unit)? = null) {
            itemView.apply {
                currencyName.text = currency.currencyName
                buyingRate.text = String.format(FORMAT_CURRENCY_LIST_RATES, currency.buyingRate)
                middleRate.text = String.format(FORMAT_CURRENCY_LIST_RATES, currency.middleRate)
                sellingRate.text = String.format(FORMAT_CURRENCY_LIST_RATES, currency.sellingRate)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        return CurrencyListViewHolder(parent.inflate(R.layout.list_item_currency))
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        holder.bindItem(items[position], itemClickListener)
    }

    override fun getItemCount() = items.size
}