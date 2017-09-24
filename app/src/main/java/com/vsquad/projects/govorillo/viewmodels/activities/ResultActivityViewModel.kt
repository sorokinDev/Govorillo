package com.vsquad.projects.govorillo.viewmodels.activities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelStoreOwner
import com.vsquad.projects.govorillo.models.entities.SpeechRating

/**
 * Created by Vova on 23.09.2017.
 */
class ResultActivityViewModel(): ViewModel() {
    var speechRating: SpeechRating? = null
}