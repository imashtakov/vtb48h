package com.example.vtbhackathonproject.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import java.util.*

class FragmentNavigator(private val fm: FragmentManager) {

    fun moveTo(fragment: Fragment, addTransactionToBackStack: Boolean, containerId: Int) {
        val transaction = fm.beginTransaction().replace(containerId, fragment, fragment.javaClass.simpleName)
        if (addTransactionToBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }
    }

    fun backAt(transactionName : String) {
        fm.popBackStack(transactionName, 0)
    }

    fun back() {
        fm.popBackStack()
    }
}