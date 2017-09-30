package com.vsquad.projects.govorillo.ui.fragments

import android.os.Bundle
import android.view.View
import com.vsquad.projects.govorillo.ui.base.BaseLifecycleFragment
import com.vsquad.projects.govorillo.viewmodels.fragments.MainFragmentViewModel
import com.vsquad.projects.govorillo.viewmodels.fragments.TwisterViewModel
import kotlinx.android.synthetic.main.fragment_twister.*

/**
 * Created by Vova on 30.09.2017.
 */
class TwisterFragment()
    : BaseLifecycleFragment<TwisterViewModel>() {

    override val viewModelClass: Class<TwisterViewModel> = TwisterViewModel::class.java

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_start_twister.setOnClickListener {
            viewModel.SwitchRecording()
            layout_start.visibility = View.GONE
            layout_stop_next.visibility = View.VISIBLE

        }
    }



    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}