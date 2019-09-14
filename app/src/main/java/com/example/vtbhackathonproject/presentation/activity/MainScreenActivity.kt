package com.example.vtbhackathonproject.presentation.activity

import com.example.vtbhackathonproject.presentation.base.BaseActivity
import com.example.vtbhackathonproject.repository.MainScreenRepository

class MainScreenActivity : BaseActivity<MainScreenRepository>(){



    override fun initRepository(): MainScreenRepository = MainScreenRepository()
}