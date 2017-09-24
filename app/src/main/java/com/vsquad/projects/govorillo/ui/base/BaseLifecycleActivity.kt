package com.vsquad.projects.govorillo.ui.base

import android.arch.lifecycle.*
import android.support.v7.app.AppCompatActivity
import com.vsquad.projects.govorillo.unsafeLazy

/**
 * Created by Vova on 11.09.2017.
 */
abstract class BaseLifecycleActivity<T : ViewModel> : AppCompatActivity(), LifecycleRegistryOwner {

    abstract val viewModelClass: Class<T>
    protected val viewModel: T by unsafeLazy { ViewModelProviders.of(this).get(viewModelClass) }

    private val registry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = registry
}