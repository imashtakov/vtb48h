package com.example.vtbhackathonproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vtbhackathonproject.Payment
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.model.CheckListModel
import com.example.vtbhackathonproject.presentation.activity.LoginActivity
import com.example.vtbhackathonproject.presentation.adapter.CheckListAdapter
import com.example.vtbhackathonproject.presentation.base.BaseFragment
import com.example.vtbhackathonproject.repository.LoginActivityRepository
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_checklist.*
import java.util.concurrent.TimeUnit

class CheckListFragment(private val repository: LoginActivityRepository) : BaseFragment<CheckListModel>(repository) {

    lateinit var adapter : CheckListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_checklist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as LoginActivity).myToolbar.title = "Митапы"
        adapter = CheckListAdapter()
        rvChecks.adapter = adapter
        unsubscribeAfterward(model.getUserPayments(repository.userName!!)
            .repeatWhen { observable -> observable.delay(60, TimeUnit.SECONDS) }
            .subscribe ( { result ->
                adapter.payments = result
                fab.show()
            }, {}))
        fab.setOnClickListener {
            navigator.moveTo(QrCodeFragment(repository), true, R.id.container)
        }

    }

    override fun initModel(): CheckListModel = CheckListModel()

    companion object {
        val TAG = CheckListFragment::class.simpleName
    }

}