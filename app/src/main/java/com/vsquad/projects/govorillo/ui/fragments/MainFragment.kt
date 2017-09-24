package com.vsquad.projects.govorillo.ui.fragments


import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder

import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.RecordingState
import com.vsquad.projects.govorillo.obs
import com.vsquad.projects.govorillo.ui.activities.RatingActivity
import com.vsquad.projects.govorillo.ui.base.BaseLifecycleFragment
import com.vsquad.projects.govorillo.viewmodels.fragments.MainFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment(override val viewModelClass: Class<MainFragmentViewModel> = MainFragmentViewModel::class.java)
    : BaseLifecycleFragment<MainFragmentViewModel>() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_speaking.setOnClickListener { viewModel.SwitchRecording() }
        viewModel.recordingState.observe(this, obs {
            btn_speaking.text = when(it){
                RecordingState.STOPPED -> "Start!"
                RecordingState.RECORDING -> "Stop!"
                RecordingState.WAITING -> "Waiting..."
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
