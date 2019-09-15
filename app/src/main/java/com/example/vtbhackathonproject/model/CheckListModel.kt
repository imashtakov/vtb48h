package com.example.vtbhackathonproject.model

import com.example.vtbhackathonproject.Payment
import com.example.vtbhackathonproject.Payments
import com.example.vtbhackathonproject.model.base.BaseModel
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.reactivex.Single

class CheckListModel : BaseModel(){

    private val gson = Gson()

    fun getUserPayments(userName: String): Single<List<Payment>> = Single.create { subscriber ->
        fbFunctions.getHttpsCallable("getUserPayments")
            .call(userName)
            .addOnSuccessListener { task ->
                val data = task.data as HashMap<String, List<Any>>?
                val jsonString = data.toString()
                val payments = gson.fromJson(jsonString, Payments::class.java)
                if (!subscriber.isDisposed) {
                    if(payments != null) {
                        subscriber.onSuccess(payments.list!!)
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