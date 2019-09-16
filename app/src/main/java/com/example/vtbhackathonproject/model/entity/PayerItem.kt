package com.example.vtbhackathonproject.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PayerItem(
    val name: String,
    var amount: Int,
    val address: String) : Parcelable