package com.vsquad.projects.govorillo.ui.fragments


import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.google.gson.GsonBuilder

import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.RecordingState
import com.vsquad.projects.govorillo.obs
import com.vsquad.projects.govorillo.ui.activities.RatingActivity
import com.vsquad.projects.govorillo.ui.base.BaseLifecycleFragment
import com.vsquad.projects.govorillo.viewmodels.fragments.MainFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment()
    : BaseLifecycleFragment<MainFragmentViewModel>() {

    override val viewModelClass: Class<MainFragmentViewModel> = MainFragmentViewModel::class.java

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_speaking.setOnClickListener { viewModel.SwitchRecording() }

        viewModel.recordingState.observe(this, obs {
            when(it){
                RecordingState.STOPPED -> {
                    btn_speaking.clearAnimation()
                    btn_speaking.setImageResource(R.drawable.ic_mic_48_w)
                    pulsator.stop()
                }
                RecordingState.RECORDING -> {
                    btn_speaking.clearAnimation()
                    btn_speaking.setImageResource(R.drawable.ic_stop_48_w)
                    pulsator.start()

                }
                RecordingState.WAITING -> {
                    btn_speaking.setImageResource(R.drawable.ic_all_inclusive_48_w)
                    btn_speaking.startAnimation(AnimationUtils.loadAnimation(context, R.anim.spinning))
                    pulsator.stop()
                }
            }
        })

        viewModel.speechResult.observe(this, obs{
            if(it != null){
                val intent = Intent(context, RatingActivity::class.java)
                intent.putExtra("SpeechRating", GsonBuilder().create().toJson(it))
                context.startActivity(intent)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater!!.inflate(R.layout.fragment_main, container, false)

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}
