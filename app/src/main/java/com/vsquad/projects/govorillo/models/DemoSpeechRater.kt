package com.vsquad.projects.govorillo.models

import android.util.Log
import com.vsquad.projects.govorillo.models.entities.SpeechRating
import com.vsquad.projects.govorillo.models.interfaces.ISpeechRater
import ru.yandex.speechkit.Recognition

/**
 * Created by Vova on 23.09.2017.
 */
class DemoSpeechRater : ISpeechRater<Unit> {

    override fun rateSpeech(
            recognition: Recognition,
            soundData: List<Byte>,
            additionalParams: Unit?
    ): SpeechRating {
        Log.d("RecognitionListener", recognition.bestResultText)

        val res = SpeechRating(
                50,
                mutableListOf("Хорошо говоришь", "Хорошо говоришь", "Хорошо говоришь", "Хорошо говоришь", "Хорошо говоришь"),
                mutableListOf("Плохо говоришь", "Плохо говоришь", "Плохо говоришь", "Плохо говоришь", "Плохо говоришь"),
                mutableListOf("Очень хороший совет", "Очень хороший совет", "Очень хороший совет", "Очень хороший совет", "Очень хороший совет")
        )

        return res
    }
}