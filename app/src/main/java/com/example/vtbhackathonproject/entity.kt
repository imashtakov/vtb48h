package com.example.vtbhackathonproject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val address : String) : Parcelable