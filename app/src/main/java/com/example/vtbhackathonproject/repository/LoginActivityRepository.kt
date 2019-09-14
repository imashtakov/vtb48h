package com.example.vtbhackathonproject.repository

import android.content.SharedPreferences
import com.example.vtbhackathonproject.repository.base.BaseRepository

class LoginActivityRepository(prefereces : SharedPreferences): BaseRepository(prefereces) {

    fun saveAddress(address : String) {
        preferences.edit().putString("OwnerAddress", address).apply()
    }
}