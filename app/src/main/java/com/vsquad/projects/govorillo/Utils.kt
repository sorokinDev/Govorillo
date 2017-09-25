package com.vsquad.projects.govorillo

import android.arch.lifecycle.Observer
import android.util.Log
import android.view.View
import android.view.ViewGroup

/**
 * Created by Vova on 11.09.2017.
 */

fun <T> unsafeLazy(initializer: () -> T) : Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

fun <T> obs(act: (par:T) -> Unit) : Observer<T> = Observer() { res -> act(res!!) }

enum class RecordingState{
    WAITING, RECORDING, STOPPED
}

fun <T: View> View.viewById(id: Int): T = this.findViewById(id) as T