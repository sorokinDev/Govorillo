package com.vsquad.projects.govorillo.models.interfaces

import com.vsquad.projects.govorillo.models.entities.SpeechRating
import ru.yandex.speechkit.Recognition

/**
 * Created by Vova on 10.09.2017.
 */
interface ISpeechRater<T> {
    fun rateSpeech(recognition: Recognition, soundData: List<Byte>, additionalParams: T? = null): SpeechRating

}