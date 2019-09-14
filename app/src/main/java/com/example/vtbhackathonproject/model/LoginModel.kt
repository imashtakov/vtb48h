package com.example.vtbhackathonproject.model

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.vtbhackathonproject.User
import com.example.vtbhackathonproject.model.base.BaseModel
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe

class LoginModel : BaseModel() {

    fun getUserAddress(userName: String): Single<String> = Single.create {subscriber ->
        fbFunctions.getHttpsCallable("getUserAddress")
            .call(userName)
            .addOnSuccessListener { task ->
                val data = task.data as HashMap<String, String>?
                val address = data?.get("address")
                if (!subscriber.isDisposed) {
                    if(address != null) {
                        subscriber.onSuccess(address)
                    } else {
                        subscriber.onError(Throwable("User not found"))
                    }
                }
            }
            .addOnFailureListener { task ->
                task.stackTrace
            }
    }
}