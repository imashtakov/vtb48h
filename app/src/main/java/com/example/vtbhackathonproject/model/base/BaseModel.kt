package com.example.vtbhackathonproject.model.base

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions

abstract class BaseModel {
    protected val fbFunctions = FirebaseFunctions.getInstance()
}