package com.vsquad.projects.govorillo

import android.arch.lifecycle.Observer
import android.util.Log

/**
 * Created by Vova on 11.09.2017.
 */

fun <T> unsafeLazy(initializer: () -> T) : Lazy<T> {

    return lazy(LazyThreadSafetyMode.NONE, initializer)
}

fun <T> obs(act: (par:T) -> Unit) : Observer<T> {
    return Observer<T>(){ res ->
        act(res!!)
    }
}

enum class RecordingState{
    WAITING, RECORDING, STOPPED
}