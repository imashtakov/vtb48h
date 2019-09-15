package com.example.vtbhackathonproject.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreatePaymentRequest(val username: String,
                                val payment: Payment) : Parcelable{
}