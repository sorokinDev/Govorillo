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

class MainActivity(override val viewModelClass: Class<MainActivityViewModel> = MainActivityViewModel::class.java)
    : BaseLifecycleActivity<MainActivityViewModel>(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle result_navigation view item clicks here.
        val id = item.itemId

        val frId = when(id){
            R.id.nav_main -> R.integer.fragment_main
            else -> R.integer.fragment_main
        }

        navigateToFragment(frId)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun navigateToFragment(id: Int){
        val fr = when(id){
            R.integer.fragment_main -> MainFragment.newInstance()
            else -> Fragment()
        }


        supportFragmentManager.beginTransaction().replace(R.id.fl_content, fr).commit()
    }
}
