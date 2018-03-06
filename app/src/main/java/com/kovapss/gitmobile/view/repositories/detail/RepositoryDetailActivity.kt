package com.kovapss.gitmobile.view.repositories.detail

import android.content.Intent
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
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.gson.internal.bind.util.ISO8601Utils
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.Readme
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.entities.repository.RepositoryStatus
import com.kovapss.gitmobile.view.profile.UserProfileActivity
import com.kovapss.gitmobile.view.repositories.detail.content.RepositoryDetailContentFragment
import com.kovapss.gitmobile.view.repositories.detail.files.RepositoryFilesFragment
import com.kovapss.gitmobile.view.repositories.detail.issues.RepositoryIssuesFragment
import com.kovapss.gitmobile.view.repositories.detail.readme.RepositoryReadmeFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_repository_detail.*
import java.text.ParsePosition

class RepositoryDetailActivity : MvpAppCompatActivity(), RepositoryDetailView {

    @InjectPresenter
    lateinit var presenter: RepositoryDetailPresenter

    companion object {
        const val REPOSITORY_KEY = "repository_key"
    }

    private lateinit var starMenuItem: MenuItem

    private lateinit var watchMenuItem: MenuItem

    @ProvidePresenter
    fun providePresenter() = RepositoryDetailPresenter(intent.getParcelableExtra(REPOSITORY_KEY))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_detail)
        repo_detail_bottom_navview.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_readme -> presenter.menuReadmeClicked()
                R.id.menu_files -> presenter.menuFilesClicked()
                R.id.menu_repo_issues -> presenter.menuIssuesClicked()
                R.id.menu_commits -> presenter.menuCommitsClicked()
                R.id.menu_contributors -> presenter.menuContributorsClicked()
            }
            true
        }
        setSupportActionBar(repo_detail_toolbar as Toolbar)
        checkNotNull(supportActionBar).apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        repo_detail_avatar.setOnClickListener { presenter.clickOwnerAvatar() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.repository_detail_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_repo_starring -> presenter.clickStar()
            R.id.menu_repo_watching -> presenter.clickWatch()
            android.R.id.home -> onBackPressed()
        }

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        starMenuItem = menu.findItem(R.id.menu_repo_starring)
        watchMenuItem = menu.findItem(R.id.menu_repo_watching)
        return true
    }

    override fun setRepositoryData(repository: Repository) {
        checkNotNull(supportActionBar).apply { title = repository.fullName }
        with(repository) {
            Picasso.with(this@RepositoryDetailActivity)
                    .load(owner.avatarUrl)
                    .noFade()
                    .into(repo_detail_avatar)

            if (language != null) {
                repo_detail_language.text = repository.language
            } else repo_detail_language.visibility = View.INVISIBLE

            repo_detail_star_count.text = stargazersCount.toString()

            repo_detail_watcher_count.text = watchers.toString()

            repo_detail_fork_count.text = forks.toString()

            repo_detail_last_update.text =
                    "Updated ${TimeAgo.using(ISO8601Utils.parse(updateDate, ParsePosition(0)).time)}"


        }

    }

    override fun setRepositoryStatus(repositoryStatus: RepositoryStatus) {

        if (repositoryStatus.starred) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                starMenuItem.icon = getDrawable(R.drawable.ic_star_primary)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                starMenuItem.icon = getDrawable(R.drawable.ic_star_border)
            }
        }
        if (repositoryStatus.watched){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                starMenuItem.icon = getDrawable(R.drawable.ic_watch_primary)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                starMenuItem.icon = getDrawable(R.drawable.ic_watch)
            }
        }

    }

    override fun showRepositoryTopics(topics: List<String>) {
        topics_layout.setTags(topics)
    }

    override fun showReadmeScreen(readme: Readme) {
        showFragment(RepositoryReadmeFragment.getInstance(readme))
    }

    override fun showIssuesScreen(ownerLogin: String, repositoryName: String) {
        showFragment(RepositoryIssuesFragment.getInstance(ownerLogin, repositoryName))
    }

    override fun showContentScreen(ownerLogin: String, repositoryName: String, dataType: Int) {
        showFragment(RepositoryDetailContentFragment.getInstance(ownerLogin, repositoryName, dataType))
    }

    override fun showFilesScreen(login: String, repositoryName: String) {
        showFragment(RepositoryFilesFragment.getInstance(login, repositoryName))
    }

    override fun openUserScreen(username: String) {
        val intent = Intent(this, UserProfileActivity::class.java).apply {
            putExtra(UserProfileActivity.USERNAME_KEY, username)
        }
        startActivity(intent)
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

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.repository_container_layout, fragment).commit()
    }

}
