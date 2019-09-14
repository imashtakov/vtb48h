package com.example.vtbhackathonproject.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vtbhackathonproject.repository.base.BaseRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity<R : BaseRepository> : AppCompatActivity() {

    lateinit var repository: R

    protected val disposables = CompositeDisposable()

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = if (savedInstanceState != null) {
            lastCustomNonConfigurationInstance as R
        } else {
            initRepository()
        }

    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return repository
    }

    protected fun unsubscribeAfterward(disposable: Disposable) {
        disposables.add(disposable)
    }

    protected abstract fun initRepository() : R
}