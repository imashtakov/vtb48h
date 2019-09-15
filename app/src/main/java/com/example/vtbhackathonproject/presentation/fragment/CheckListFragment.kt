package com.example.vtbhackathonproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vtbhackathonproject.Payment
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.model.CheckListModel
import com.example.vtbhackathonproject.presentation.adapter.CheckListAdapter
import com.example.vtbhackathonproject.presentation.base.BaseFragment
import com.example.vtbhackathonproject.repository.LoginActivityRepository
import kotlinx.android.synthetic.main.fragment_checklist.*

class CheckListFragment(private val repository: LoginActivityRepository) : BaseFragment<CheckListModel>(repository) {

    lateinit var adapter : CheckListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_checklist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CheckListAdapter()
        adapter.payments = listOf(
            Payment(emptyList(), "", 24, 24),
            Payment(emptyList(), "", 24, 24),
            Payment(emptyList(), "", 24, 24),
            Payment(emptyList(), "", 24, 24)
        )
        rvChecks.adapter = adapter
        unsubscribeAfterward(model.getUserPayments(repository.userName!!)
            .subscribe ( {

            }, {}))
        fab.setOnClickListener {
            repository.userName?.let {
                model.getUserPayments(it)
            }
        }
    }

    override fun initModel(): CheckListModel = CheckListModel()
}