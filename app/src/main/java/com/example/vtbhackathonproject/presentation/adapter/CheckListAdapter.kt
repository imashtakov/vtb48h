package com.example.vtbhackathonproject.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vtbhackathonproject.Payment
import com.example.vtbhackathonproject.R

class CheckListAdapter : RecyclerView.Adapter<CheckListAdapter.CheckListViewHolder>() {

    private lateinit var payments : List<Payment>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListViewHolder =
        CheckListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_checklist, parent, false))

    override fun getItemCount(): Int = payments.size

    override fun onBindViewHolder(holder: CheckListViewHolder, position: Int) {
        holder.bind(payments.get(position))
    }

    class CheckListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(payment: Payment) {

        }
    }
}