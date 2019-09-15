package com.example.vtbhackathonproject.repository

import android.content.SharedPreferences
import com.example.vtbhackathonproject.Receipt
import com.example.vtbhackathonproject.repository.base.BaseRepository

class LoginActivityRepository(prefereces : SharedPreferences): BaseRepository(prefereces) {

    var userName : String? = null
    var receipt : Receipt? = null
    var sum: Int? = 420
    var currency: Int? = null

    fun saveAddress(address : String) {
        preferences.edit().putString("OwnerAddress", address).apply()
    }
}