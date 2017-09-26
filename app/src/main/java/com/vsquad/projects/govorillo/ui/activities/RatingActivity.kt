package com.vsquad.projects.govorillo.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView

import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.models.entities.SpeechRating
import com.vsquad.projects.govorillo.ui.base.BaseLifecycleActivity
import com.vsquad.projects.govorillo.viewById
import com.vsquad.projects.govorillo.viewmodels.activities.ResultActivityViewModel

import kotlinx.android.synthetic.main.activity_rating.*
import org.w3c.dom.Text

class RatingActivity
    : BaseLifecycleActivity<ResultActivityViewModel>() {

    override val viewModelClass: Class<ResultActivityViewModel> = ResultActivityViewModel::class.java

    lateinit private var mSectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        if(viewModel.speechRating == null)
            viewModel.speechRating = Gson().fromJson(intent.getStringExtra("SpeechRating"), SpeechRating::class.java)

        setSupportActionBar(toolbar)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter

        tabs.setupWithViewPager(container)
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
            speechRating = Gson().fromJson(arguments.getString(ARG_CONTENT), SpeechRating::class.java)
            val secN = arguments.getInt(ARG_SECTION_NUMBER)

            var rootView: View = View(inflater!!.context)
            when(secN){
                0 -> {
                    rootView = inflater!!.inflate(R.layout.fragment_rating_main, container, false)
                    rootView.viewById<TextView>(R.id.tv_points).text = speechRating.points.toString() + "/100"
                }
                1 -> {
                    rootView = inflater!!.inflate(R.layout.fragment_rating_pros_cons, container, false)
                    rootView.viewById<ListView>(R.id.list_pros_cons).adapter =
                            SimpleAdapter(inflater, speechRating.pros)
                }
                2 -> {
                    rootView = inflater!!.inflate(R.layout.fragment_rating_pros_cons, container, false)
                    rootView.viewById<ListView>(R.id.list_pros_cons).adapter =
                            SimpleAdapter(inflater, speechRating.cons)
                }
                3 -> {
                    rootView = inflater!!.inflate(R.layout.fragment_rating_pros_cons, container, false)
                    rootView.viewById<ListView>(R.id.list_pros_cons).adapter =
                            SimpleAdapter(inflater, speechRating.advices)
                }
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

    class SimpleAdapter(private val inflater: LayoutInflater, lst: List<String>) : BaseAdapter() {
        private var Lst = ArrayList<String>()

        init {
            this.Lst = lst as ArrayList<String>
        }

        override fun getCount(): Int = Lst.size

        override fun getItem(i: Int): Any = i

        override fun getItemId(i: Int): Long = i.toLong()

        @SuppressLint("InflateParams", "ViewHolder")
        override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
            val vi = inflater.inflate(android.R.layout.simple_list_item_1, null)
            vi.viewById<TextView>(android.R.id.text1).text = Lst[i]
            return vi
        }
    }
}
