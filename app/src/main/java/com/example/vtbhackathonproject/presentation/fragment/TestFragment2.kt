package com.example.vtbhackathonproject.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vtbhackathonproject.R
import com.example.vtbhackathonproject.model.TestModel
import com.example.vtbhackathonproject.presentation.base.BaseFragment
import com.example.vtbhackathonproject.repository.MainActivityRepository

class TestFragment2(repository: MainActivityRepository) : BaseFragment<TestModel>(repository) {

    companion object {
        val TAG = TestFragment2::class.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test2, container, false)
    }

    override fun initModel(): TestModel = TestModel()
}