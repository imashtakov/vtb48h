package com.example.vtbhackathonproject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val address: String) : Parcelable

@Parcelize
data class Payment(
    val participants: List<Participant>,
    val description: String,
    val overallCost: Int,
    val status: Int
) :
    Parcelable

@Parcelize
data class Participant(val address: String, val amount: Int, val status: Int) : Parcelable