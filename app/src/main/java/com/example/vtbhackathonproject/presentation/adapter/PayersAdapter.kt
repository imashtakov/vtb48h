package com.example.vtbhackathonproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.model.entity.PayerItem
import com.example.vtbhackathonproject.presentation.adapter.holder.PayerItemViewHolder

class PayersAdapter: RecyclerView.Adapter<PayerItemViewHolder>() {

    private var payerItems = ArrayList<PayerItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayerItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_payer, parent, false)

        return PayerItemViewHolder(view)
    }

    override fun getItemCount(): Int = payerItems.size

    override fun onBindViewHolder(holder: PayerItemViewHolder, position: Int) {
        holder.bind(payerItems[position])
    }

    fun addPayer(name: String) {
        payerItems.add(PayerItem(name, 0))
    }

    fun deletePayer(position: Int) {
        payerItems.remove(payerItems[position])
    }
}