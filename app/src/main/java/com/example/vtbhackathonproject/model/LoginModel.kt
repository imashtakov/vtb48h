package com.example.vtbhackathonproject.model

import com.example.vtbhackathonproject.User
import com.example.vtbhackathonproject.model.base.BaseModel
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import io.reactivex.Single

class LoginModel(fbFunctions: FirebaseFunctions) : BaseModel(fbFunctions) {

    fun getUserAddress(userName: String): Single<User> = Single.create { subscriber ->
        val data = hashMapOf(
            "userName" to userName
        )
        fbFunctions.getHttpsCallable("getUserAddress")
            .call(data)
            .continueWith { task ->
                val result = task.result?.data as String
                print("result recieve = $result")
                result
            }.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    val exception = task.exception
                    if (exception is FirebaseFunctionsException) {
                        print(exception.code)
                        print(exception.details)
                    }
                }
            }
    }
}