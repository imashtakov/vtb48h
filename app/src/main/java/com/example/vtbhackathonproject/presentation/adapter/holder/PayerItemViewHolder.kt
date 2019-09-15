package com.example.vtbhackathonproject.presentation.adapter.holder

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.vtbhackathonproject.model.entity.PayerItem
import kotlinx.android.synthetic.main.item_payer.view.*

class PayerItemViewHolder(itemView: View, private var amountChangeLister: PayerAmountChangeLister? = null) :
    RecyclerView.ViewHolder(itemView) {

    private lateinit var payerItem: PayerItem

    fun bind(payerItem: PayerItem) {
        this.payerItem = payerItem

        itemView.payerName.text = payerItem.name
        itemView.etMoneyValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text?.isNotEmpty().let {
                    val newAmount = text.toString().toInt()
                    val diff = payerItem.amount - newAmount
                    payerItem.amount = newAmount
                    amountChangeLister?.onPayerAmountChange(diff)
                }
            }
        })
    }

    interface PayerAmountChangeLister {
        fun onPayerAmountChange(diff: Int)
    }
}