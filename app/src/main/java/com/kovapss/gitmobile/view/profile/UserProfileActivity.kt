package com.kovapss.gitmobile.view.profile


import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.view.profile.content.UserProfileContentFragment
import com.kovapss.gitmobile.view.profile.overview.ProfileOverviewFragment
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_user_profile.*


class UserProfileActivity : MvpAppCompatActivity(), UserProfileView {

    @InjectPresenter lateinit var presenter: ProfilePresenter

    companion object {
        const val USERNAME_KEY : String = "username_key"
    }

    @ProvidePresenter
    fun providePresenter() : ProfilePresenter =
            ProfilePresenter(intent.getStringExtra(USERNAME_KEY))

    private lateinit var followMenuItem: MenuItem

    private lateinit var blockMenuItem : MenuItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        user_profile_bottom_navview.isEnabled = false
        setSupportActionBar(user_profile_toolbar as Toolbar)
        with(checkNotNull(supportActionBar)){
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        user_profile_bottom_navview.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.menu_profile_overview -> presenter.overviewMenuOnClick()
                R.id.menu_profile_repos ->
                    presenter.reposMenuOnClick(UserProfileContentFragment.REPOSITORY_DATA_TYPE)
                R.id.menu_profile_starred ->
                    presenter.starredMenuOnClick(UserProfileContentFragment.STARRED_DATA_TYPE)
                R.id.menu_profile_gists ->
                    presenter.gistsMenuOnClick(UserProfileContentFragment.GISTS_DATA_TYPE)
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.user_profile_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        followMenuItem = menu.findItem(R.id.menu_follow)
        blockMenuItem = menu.findItem(R.id.menu_block)
        presenter.optionsMenuPrepared()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> onBackPressed()
            R.id.menu_block -> presenter.blockClicked()
            R.id.menu_follow -> presenter.followClicked()
        }
        return true
    }

    override fun setUserLogin(login: String) {
        with(checkNotNull(supportActionBar)){ title = login }
    }

    override fun showOverviewScreen(user: User) {
        val bundle = Bundle()
        bundle.putParcelable(ProfileOverviewFragment.USER_KEY, user)
        showFragment(ProfileOverviewFragment.getInstance(bundle))
        user_profile_bottom_navview.isEnabled = true
    }

    override fun showProfileContentScreen(dataType: Int, username: String) {
        Logger.d("showProfileContentScreen method, dataType: $dataType")
        val bundle = Bundle().apply {
            putInt(UserProfileContentFragment.DATA_TYPE, dataType)
            putString(UserProfileContentFragment.USERNAME_KEY, username )
        }
        showFragment(UserProfileContentFragment.getInstance(bundle))
    }

    override fun setUserFollowedStatus(isFollowed: Boolean) {
        if (isFollowed) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                followMenuItem.icon = getDrawable(R.drawable.ic_followed_user)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                followMenuItem.icon = getDrawable(R.drawable.ic_follow)
            }
        }
    }

    override fun setUserBlockedStatus(isBlocked: Boolean) {
        if (isBlocked) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               blockMenuItem.icon = getDrawable(R.drawable.ic_watch_primary)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               blockMenuItem.icon = getDrawable(R.drawable.ic_block)
            }
        }
    }

    override fun showActionsMenu() {
        followMenuItem.isVisible = true
        blockMenuItem.isVisible = true
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.INVISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showFragment(fragment : Fragment){
        supportFragmentManager
                .beginTransaction().replace(R.id.profile_container_layout, fragment)
                .commit()
    }

}
