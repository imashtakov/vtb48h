package com.example.vtbhackathonproject.presentation.activity

import android.os.Bundle
import android.preference.PreferenceManager
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.presentation.FragmentNavigator
import com.example.vtbhackathonproject.presentation.base.BaseActivity
import com.example.vtbhackathonproject.presentation.fragment.DistributeBillFragment
import com.example.vtbhackathonproject.presentation.fragment.LoginFragment
import com.example.vtbhackathonproject.repository.LoginActivityRepository
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginActivityRepository>() {

    companion object {
        val TAG = LoginActivity::class.simpleName
    }

    lateinit var navigator: FragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(myToolbar)
        setContentView(R.layout.activity_login)
        navigator = FragmentNavigator(supportFragmentManager)
        navigator.moveTo(LoginFragment(repository), false, R.id.container)
//             navigator.moveTo(DistributeBillFragment(repository), false, R.id.container)
    }

    override fun initRepository(): LoginActivityRepository =
        LoginActivityRepository(PreferenceManager.getDefaultSharedPreferences(this))
}