package com.example.vtbhackathonproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.model.LoginModel
import com.example.vtbhackathonproject.presentation.base.BaseFragment
import com.example.vtbhackathonproject.repository.LoginActivityRepository
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.android.synthetic.main.fragment_login_phone.*

class LoginFragment(repository: LoginActivityRepository) : BaseFragment<LoginModel>(repository) {

    companion object {
        val TAG = LoginFragment::class.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendBtn.setOnClickListener {
            model.getUserAddress(etLoginName.text.toString())
        }
    }

    override fun initModel(): LoginModel = LoginModel(FirebaseFunctions.getInstance())
}