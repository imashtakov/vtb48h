package com.example.vtbhackathonproject.model.entity

data class Payment(val participants: ArrayList<PayerItem>,
                   val description: String,
                   val ownerAmount: Int,
                   val overallCost: Int) {
}