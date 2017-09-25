package com.vsquad.projects.govorillo.ui.activities

import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.vsquad.projects.govorillo.R
import com.vsquad.projects.govorillo.ui.base.BaseLifecycleActivity
import com.vsquad.projects.govorillo.ui.fragments.MainFragment
import com.vsquad.projects.govorillo.viewmodels.activities.MainActivityViewModel

class MainActivity
    : BaseLifecycleActivity<MainActivityViewModel>() {

    override val viewModelClass: Class<MainActivityViewModel> = MainActivityViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener {
            val frId = when(it.itemId){
                R.id.nav_main -> R.integer.fragment_main
                else -> R.integer.fragment_main
            }
            navigateToFragment(frId)

            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        // For default fragment
        nav_view.menu.findItem(R.id.nav_main).isChecked = true
        navigateToFragment(R.integer.fragment_main)

    }

    private fun navigateToFragment(id: Int){
        val fr = when(id){
            R.integer.fragment_main -> MainFragment.newInstance()
            else -> Fragment()
        }

        supportFragmentManager.beginTransaction().replace(R.id.fl_content, fr).commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START))  drawer_layout.closeDrawer(GravityCompat.START)
        else super.onBackPressed()
    }
}
