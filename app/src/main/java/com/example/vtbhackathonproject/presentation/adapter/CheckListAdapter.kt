package com.example.vtbhackathonproject.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vtbhackathonproject.Payment
import com.example.vtbhackathonproject.R

class CheckListAdapter : RecyclerView.Adapter<CheckListAdapter.CheckListViewHolder>() {

    var payments : List<Payment>? = emptyList()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListViewHolder =
        CheckListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_checklist, parent, false))

    override fun getItemCount(): Int = payments!!.size

    override fun onBindViewHolder(holder: CheckListViewHolder, position: Int) {
        holder.bind(payments!![position])
    }

    class CheckListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvRestarauntName : TextView = itemView.findViewById(R.id.tvRestaurantName)
        private val tvSum = itemView.findViewById<TextView>(R.id.tvSum)
        private val ivStatus = itemView.findViewById<ImageView>(R.id.ivStatus)

        fun bind(payment: Payment) {
            tvRestarauntName.text = payment.status.toString()
            tvSum.text = payment.overallCost.toString()
            ivStatus.setImageResource(getDrawable(payment.status!!))
        }

        private fun getDrawable(status : Int) : Int = when(status) {
            0 -> R.drawable.ic_clear_black_24dp
            1 -> R.drawable.ic_keyboard_arrow_down_black_24dp
            else -> R.drawable.ic_hourglass_24dp
        }

    }
}