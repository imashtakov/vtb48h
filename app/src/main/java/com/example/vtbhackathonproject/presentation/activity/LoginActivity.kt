package com.example.vtbhackathonproject.presentation.activity

import android.os.Bundle
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.presentation.FragmentNavigator
import com.example.vtbhackathonproject.presentation.base.BaseActivity
import com.example.vtbhackathonproject.presentation.fragment.LoginFragment
import com.example.vtbhackathonproject.repository.LoginActivityRepository
import com.google.firebase.FirebaseApp

class LoginActivity : BaseActivity<LoginActivityRepository>() {

    companion object {
        val TAG = LoginActivity::class.simpleName
    }

    lateinit var navigator : FragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FirebaseApp.initializeApp(this)
        navigator = FragmentNavigator(supportFragmentManager)
        navigator.moveTo(LoginFragment(repository), false, R.id.container)
    }

    override fun initRepository(): LoginActivityRepository = LoginActivityRepository()
}