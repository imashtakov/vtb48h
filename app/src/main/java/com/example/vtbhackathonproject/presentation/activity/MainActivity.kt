package com.example.vtbhackathonproject.presentation.activity

import android.content.Intent
import android.os.Bundle
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.presentation.FragmentNavigator
import com.example.vtbhackathonproject.presentation.base.BaseActivity
import com.example.vtbhackathonproject.presentation.fragment.LoginPhoneFragment
import com.example.vtbhackathonproject.repository.MainActivityRepository

class MainActivity : BaseActivity<MainActivityRepository>() {

    companion object {
        val TAG = MainActivity::class.simpleName
    }

    lateinit var navigator : FragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator = FragmentNavigator(supportFragmentManager)
        startLoginActivity()
    }

    override fun initRepository(): MainActivityRepository = MainActivityRepository()

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}