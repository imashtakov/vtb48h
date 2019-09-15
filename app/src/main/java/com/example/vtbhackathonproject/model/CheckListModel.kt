package com.example.vtbhackathonproject.model

import com.example.vtbhackathonproject.Payment
import com.example.vtbhackathonproject.model.base.BaseModel
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Single

class CheckListModel : BaseModel(){

    fun getUserPayments(userName: String): Single<List<Payment>> = Single.create { subscriber ->
        fbFunctions.getHttpsCallable("getUserPayments")
            .call(userName)
            .addOnSuccessListener { task ->
                val data = task.data as HashMap<String, List<Any>>?
                val mapper = ObjectMapper()
                mapper.convertValue<Payment>(data!!.get("list")!!.get(0), object : TypeReference<Payment>() {

                })
                val payments = data["list"]
                if (!subscriber.isDisposed) {
                    if(payments != null) {
//                        subscriber.onSuccess(address)
                    } else {
//                        subscriber.onError(Throwable("User not found"))
                    }
                }
            }
            .addOnFailureListener { task ->
                task.stackTrace
            }
    }
}