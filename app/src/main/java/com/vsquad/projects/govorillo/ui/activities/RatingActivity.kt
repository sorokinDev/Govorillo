package com.vsquad.projects.govorillo.ui.activities

import android.support.design.widget.TabLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.models.entities.SpeechRating
import com.vsquad.projects.govorillo.ui.base.BaseLifecycleActivity
import com.vsquad.projects.govorillo.viewmodels.activities.ResultActivityViewModel

class RatingActivity(override val viewModelClass: Class<ResultActivityViewModel> = ResultActivityViewModel::class.java)
    : BaseLifecycleActivity<ResultActivityViewModel>() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private var mViewPager: ViewPager? = null
    private lateinit var speechRating: SpeechRating

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        if(viewModel.speechRating == null)
            viewModel.speechRating = Gson().fromJson(intent.getStringExtra("SpeechRating"), SpeechRating::class.java)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        mViewPager = findViewById(R.id.container) as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment =
                PlaceholderFragment.newInstance(position, viewModel.speechRating!!)

        override fun getCount(): Int = 4

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> "Main"
            1 -> "Pros"
            2 -> "Cons"
            3 -> "Advices"
            else -> null
        }
    }

    class PlaceholderFragment : Fragment() {
        lateinit var speechRating: SpeechRating
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater!!.inflate(R.layout.fragment_rating_content, container, false)
            val textView = rootView.findViewById(R.id.section_label) as TextView
            speechRating = Gson().fromJson(arguments.getString(ARG_CONTENT), SpeechRating::class.java)
            val secN = arguments.getInt(ARG_SECTION_NUMBER)
            textView.text = getString(R.string.section_format, arguments.getInt(ARG_SECTION_NUMBER)) +
                    "\n" + when(secN){
                0 -> speechRating.points
                1 -> speechRating.pros.toString()
                2 -> speechRating.cons.toString()
                3 -> speechRating.advices.toString()
                else -> "None"
            }
            return rootView
        }

        companion object {
            private val ARG_SECTION_NUMBER = "section_number"
            private val ARG_CONTENT = "section_content"

            fun newInstance(sectionNumber: Int, sectionContent: SpeechRating): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                args.putString(ARG_CONTENT, GsonBuilder().create().toJson(sectionContent))
                fragment.arguments = args
                return fragment
            }
        }
    }
}
