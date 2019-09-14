package com.example.vtbhackathonproject.presentation.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.vtbhackathonproject.model.base.BaseModel
import com.example.vtbhackathonproject.presentation.FragmentNavigator
import com.example.vtbhackathonproject.repository.MainActivityRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<M : BaseModel>(private val activityRepository: MainActivityRepository) : Fragment() {

    lateinit var model: M
    lateinit var navigator: FragmentNavigator
    private val disposables = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = initModel()
        navigator = FragmentNavigator(activity!!.supportFragmentManager)
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    protected fun unsubscribeAfterward(disposable: Disposable) {
        disposables.add(disposable)
    }

    protected abstract fun initModel(): M
}