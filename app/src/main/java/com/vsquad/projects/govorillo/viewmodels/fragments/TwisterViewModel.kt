package com.vsquad.projects.govorillo.viewmodels.fragments

import android.app.Application
import com.vsquad.projects.govorillo.models.DemoSpeechRater
import com.vsquad.projects.govorillo.models.interfaces.ISpeechRater
import com.vsquad.projects.govorillo.viewmodels.base.BaseRecordingViewModel

/**
 * Created by Vova on 30.09.2017.
 */
class TwisterViewModel(application: Application)
    : BaseRecordingViewModel<DemoSpeechRater, Unit>(application) {
    override var speechRater: ISpeechRater<Unit> = DemoSpeechRater()

    fun nextTwister(): String{
        return "Шла Саша по шоссе и сосала сушку"
    }
}