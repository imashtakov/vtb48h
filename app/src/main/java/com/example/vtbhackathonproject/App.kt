package com.example.vtbhackathonproject

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {

    lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }
}