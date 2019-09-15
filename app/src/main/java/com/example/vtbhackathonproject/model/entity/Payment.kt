package com.example.vtbhackathonproject.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Payment(val participants: ArrayList<PayerItem>,
                   val description: String,
                   val ownerAmount: Int,
                   val overallCost: Int) : Parcelable{
}