package com.kovapss.gitmobile.main

import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.FiltersData
import com.kovapss.gitmobile.gists.GistsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_view_header.*


class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var toggle: ActionBarDrawerToggle

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun setUserData(avatarUrl: String, name: String) {
        Picasso.with(this)
                .load(avatarUrl)
                .noFade()
                .into(avatar_image_view)
        username_text.text = name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //window.setBackgroundDrawable(null)
        prepareNavigationView()
        setSupportActionBar(toolbar)
        toggle.syncState()
        if (supportActionBar != null) {
            with(supportActionBar) {
                this?.setDisplayShowTitleEnabled(true)
                this?.setHomeButtonEnabled(true)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as android.widget.SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.isSubmitButtonEnabled = true
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val intent = Intent(Intent.ACTION_SEARCH)
                intent.putExtra(SearchManager.QUERY, p0)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean = true

        })

        return true
    }


    override fun onBackPressed() {
        if (isNavigationDrawerOpen()) {
            closeNavigationDrawer()
        } else {
            super.onBackPressed()
        }
    }

    private fun prepareNavigationView() {
        toggle = ActionBarDrawerToggle(this, drawer_layout,
                toolbar, R.string.toggle_open, R.string.toggle_closed)
        drawer_layout.addDrawerListener(toggle)
        navigation_view.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_gists -> openFragment()
                R.id.menu_repos -> openFragment()
                R.id.menu_issues -> openFragment()
                R.id.menu_apps -> openFragment()
                R.id.menu_orgs -> openFragment()
                R.id.menu_settings -> openFragment()
            }
            setToolbarTitle(item.title.toString())
            closeNavigationDrawer()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
            = toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)


    private fun isNavigationDrawerOpen(): Boolean =
            drawer_layout != null && drawer_layout.isDrawerOpen(GravityCompat.START)


    private fun closeNavigationDrawer() {
        if (drawer_layout != null) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    private fun setToolbarTitle(title: String) {
        supportActionBar!!.title = title
    }

    private fun openFragment(): Boolean {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, GistsFragment())
                .commit()
        return true
    }
}

