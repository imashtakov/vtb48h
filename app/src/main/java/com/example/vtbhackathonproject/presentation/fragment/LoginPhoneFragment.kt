package com.example.vtbhackathonproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.model.LoginPhoneModel
import com.example.vtbhackathonproject.presentation.base.BaseFragment
import com.example.vtbhackathonproject.repository.LoginActivityRepository

class LoginPhoneFragment(repository: LoginActivityRepository): BaseFragment<LoginPhoneModel>(repository) {

    companion object {
        val TAG = LoginPhoneFragment::class.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_phone, container, false)
    }

    override fun initModel(): LoginPhoneModel = LoginPhoneModel()
}