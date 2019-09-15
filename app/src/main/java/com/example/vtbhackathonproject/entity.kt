package com.example.vtbhackathonproject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val address: String) : Parcelable

@Parcelize
data class Payments(val list: ArrayList<Payment>? = null) : Parcelable

@Parcelize
data class Payment(
    val id: String? = null,
    val participants: List<Participant>? = null,
    val description: String? = null,
    val ownerAmount: Int? = null,
    val overallCost: Int? = null,
    val status: Int? = null
) :
    Parcelable

@Parcelize
data class Participant(
    val address: String? = null,
    val amount: Int? = null,
    val status: Int? = null,
    val invoiceNumber: String? = null
) : Parcelable