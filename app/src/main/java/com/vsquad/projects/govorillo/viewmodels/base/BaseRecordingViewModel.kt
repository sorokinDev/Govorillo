package com.vsquad.projects.govorillo.viewmodels.base

import android.app.Application
import android.arch.lifecycle.*
import android.content.Intent
import android.util.Log
import com.google.gson.GsonBuilder
import com.vsquad.projects.govorillo.RecordingState
import com.vsquad.projects.govorillo.models.entities.SpeechRating
import com.vsquad.projects.govorillo.models.interfaces.ISpeechRater
import com.vsquad.projects.govorillo.ui.activities.RatingActivity
import org.json.JSONObject
import ru.yandex.speechkit.Error
import ru.yandex.speechkit.Recognition
import ru.yandex.speechkit.Recognizer
import ru.yandex.speechkit.RecognizerListener
import ru.yandex.speechkit.SpeechKit

/**
 * Created by Vova on 19.09.2017.
 */
abstract class BaseRecordingViewModel<T : ISpeechRater<SRAP>, SRAP>(application: Application) : AndroidViewModel(application), RecognizerListener {
    var speechResult: MutableLiveData<SpeechRating> = MutableLiveData()
    protected var isRecording: Boolean = false
    val recordingState: MutableLiveData<RecordingState> = MutableLiveData<RecordingState>()
    protected lateinit var recognizer: Recognizer
    protected val soundData: MutableList<Byte> = mutableListOf<Byte>()

    protected abstract var speechRater: ISpeechRater<SRAP>

    init {
        recordingState.value = RecordingState.STOPPED

        Log.d("baseRecordingVM", "Configured SpeechKit")
        SpeechKit.getInstance().configure(application.applicationContext, "ff1fb365-dd6d-4670-adf1-9073ba9d297d")

    }

    fun SwitchRecording(){
        Log.d("BaseRecordingVM", "switchRec")
        if(recordingState.value == RecordingState.WAITING) return

        recordingState.value = RecordingState.WAITING

        isRecording = !isRecording

        if(isRecording){
            if(Recognizer.isRecognitionAvailable()){
                soundData.clear()
                recognizer = Recognizer.create("ru-RU", Recognizer.Model.NOTES, this, true)
                recognizer.start()
                isRecording = true
            }else{
                isRecording = false
                recordingState.value = RecordingState.STOPPED
                Log.d("BaseRecordingVM", "Recognition not enabled")

            }
        }else{
            recognizer.finishRecording()
            isRecording = false
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun StopRecording(){
        isRecording = false
        recordingState.value = RecordingState.STOPPED
        recognizer.cancel()
    }

    override fun onRecordingDone(p0: Recognizer?) {
        recordingState.value = RecordingState.WAITING

    }

    override fun onSoundDataRecorded(p0: Recognizer?, p1: ByteArray?) {
        //Log.d("RecognitionListener", "onSoundDataRecorded")
        soundData += p1!!.asList()
    }

    override fun onPowerUpdated(p0: Recognizer?, p1: Float) {
        //Log.d("RecognitionListener", "onPowerUpdated")
    }

    override fun onPartialResults(p0: Recognizer?, p1: Recognition?, p2: Boolean) {
        Log.d("RecognitionListener", "onPartialResults")
    }

    override fun onRecordingBegin(p0: Recognizer?) {
        recordingState.value = RecordingState.RECORDING
    }


    override fun onRecognitionDone(p0: Recognizer?, p1: Recognition?) {
        isRecording = false
        recordingState.value = RecordingState.STOPPED

        speechResult.value = speechRater.rateSpeech(p1!!, soundData)

        Log.d("RecognitionListener", "onRecognitionDone")
    }

    override fun onError(p0: Recognizer?, p1: Error?) {
        isRecording = false
        recordingState.value = RecordingState.STOPPED
        Log.d("RecognitionListener", "onError")
        Log.d("RecognitionListener", p1!!.string)
    }

    override fun onSpeechEnds(p0: Recognizer?) {

    }

    override fun onSpeechDetected(p0: Recognizer?) {

    }

}