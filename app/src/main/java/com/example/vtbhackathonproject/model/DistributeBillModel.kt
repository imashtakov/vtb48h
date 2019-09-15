package com.example.vtbhackathonproject.model

import com.example.vtbhackathonproject.model.base.BaseModel
import io.reactivex.Single

class DistributeBillModel: BaseModel() {

    fun getUserAddress(userName: String): Single<String> = Single.create { subscriber ->
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