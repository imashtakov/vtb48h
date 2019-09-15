package com.example.vtbhackathonproject.presentation.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.model.LoginModel
import com.example.vtbhackathonproject.presentation.activity.LoginActivity
import com.example.vtbhackathonproject.presentation.base.BaseFragment
import com.example.vtbhackathonproject.repository.LoginActivityRepository
import com.example.vtbhackathonproject.utils.ViewUtils
import com.google.firebase.functions.FirebaseFunctions
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_login_phone.*

class LoginFragment(private val repository: LoginActivityRepository) : BaseFragment<LoginModel>(repository) {

    companion object {
        val TAG = LoginFragment::class.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as LoginActivity).myToolbar.title = "Ввод имени"
        sendBtn.setOnClickListener {
            model.getUserAddress(etLoginName.text.toString())
                .subscribe({ address ->
                    repository.userName = etLoginName.text.toString()
                    repository.saveAddress(address)
                    navigator.moveTo(CheckListFragment(repository), true, R.id.container)
                }, {})
        }

    }

    override fun initModel(): LoginModel =
        LoginModel()
}