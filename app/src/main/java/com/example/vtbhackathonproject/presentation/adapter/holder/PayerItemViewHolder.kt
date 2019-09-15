package com.example.vtbhackathonproject.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.vtbhackathonproject.model.entity.PayerItem
import kotlinx.android.synthetic.main.item_payer.view.*

class PayerItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private lateinit var payerItem: PayerItem

    fun bind(payerItem: PayerItem) {
        this.payerItem = payerItem

        itemView.payerName.text = payerItem.name
        itemView.etMoneyValue.text.toString().toInt()
    }
}