package com.kovapss.gitmobile.view.main

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kovapss.gitmobile.Constants.OAUTH_REQUEST_CODE
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.view.gists.GistsFragment
import com.kovapss.gitmobile.view.login.LoginActivity
import com.kovapss.gitmobile.view.profile.UserProfileActivity
import com.kovapss.gitmobile.view.repositories.RepositoriesFragment
import com.kovapss.gitmobile.view.settings.SettingsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_view_header.*
import kotlinx.android.synthetic.main.navigation_view_header.view.*


class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var toggle: ActionBarDrawerToggle

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun setUserData(avatarUrl: String, name: String) {
        header_root.visibility = View.VISIBLE
        Picasso.with(this)
                .load(avatarUrl)
                .noFade()
                .into(avatar_image_view)
        username_text.text = name
    }

    override fun openUserProfileScreen(username : String) {
        val intent = Intent(this, UserProfileActivity::class.java).apply {
            putExtra(UserProfileActivity.USERNAME_KEY, username)
        }
        startActivity(intent)
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

    override fun showLoginDialog() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.need_to_login))
                .setMessage(getString(R.string.main_need_to_login))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.sign_in)) { _, _ -> presenter.clickOnLogin()}
                .setNeutralButton(getString(R.string.no), {
                    p0, _ -> p0.cancel()
                })
                .create()
                .show()
    }

    override fun openLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, OAUTH_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            OAUTH_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK){
                    presenter.loginSuccessful()
                }
            }
        }
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
                R.id.menu_account -> presenter.clickOnHeader()
                R.id.menu_gists -> openFragment(GistsFragment())
                R.id.menu_repos -> openFragment(RepositoriesFragment())
                R.id.menu_about -> {}
//                R.id.menu_settings -> startActivity(Intent(this, SettingsActivity::class.java))
            }
            setToolbarTitle(item.title.toString())
            closeNavigationDrawer()
            true
        }
        navigation_view.getHeaderView(0).setOnClickListener { presenter.clickOnHeader() }
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
        checkNotNull(supportActionBar).title = title
    }

    private fun openFragment(fragment : Fragment): Boolean {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        return true
    }
}

