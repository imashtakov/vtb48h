package com.example.vtbhackathonproject.presentation.base

import androidx.fragment.app.DialogFragment
import io.reactivex.subjects.PublishSubject

abstract class BaseDialog <RESULT> : DialogFragment() {

    val result = PublishSubject.create<RESULT>()
}