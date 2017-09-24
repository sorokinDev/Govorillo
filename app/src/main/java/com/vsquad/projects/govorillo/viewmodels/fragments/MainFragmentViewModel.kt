package com.vsquad.projects.govorillo.viewmodels.fragments

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.vsquad.projects.govorillo.models.DemoSpeechRater
import com.vsquad.projects.govorillo.models.interfaces.ISpeechRater
import com.vsquad.projects.govorillo.viewmodels.base.BaseRecordingViewModel

/**
 * Created by Vova on 14.09.2017.
 */
class MainFragmentViewModel(application: Application)
    : BaseRecordingViewModel<DemoSpeechRater, Unit>(application) {
    override var speechRater: ISpeechRater<Unit> = DemoSpeechRater()

}