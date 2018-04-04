package com.kovapss.gitmobile.view.repositories.detail.issues.detail

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.gson.internal.bind.util.ISO8601Utils
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.Comment
import com.kovapss.gitmobile.entities.issue.Issue
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_issue_detail.*
import java.text.ParsePosition

class IssueDetailActivity : MvpAppCompatActivity(), IssueDetailView {

    @ProvidePresenter
    fun providePresenter() = IssueDetailPresenter(intent.getParcelableExtra(ISSUE_KEY),
            intent.getStringExtra(REPO_OWNER_LOGIN_KEY),
            intent.getStringExtra(REPOSITORY_NAME_KEY))

    @InjectPresenter lateinit var presenter: IssueDetailPresenter

    companion object {
        const val ISSUE_KEY = "issue_key"
        const val REPO_OWNER_LOGIN_KEY = "repo_owner_login_key"
        const val REPOSITORY_NAME_KEY = "repo_name_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_detail)
        setSupportActionBar(issue_detail_toolbar as Toolbar)
        checkNotNull(supportActionBar).apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        with(issue_comments_recyclerview){
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@IssueDetailActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.issue_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_open_in_browser -> presenter.openInBrowserMenuClicked()
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun showIssue(issue: Issue) {
        checkNotNull(supportActionBar).title = issue.number
        Picasso.with(issue_detail_avatar.context)
                .load(issue.author.avatarUrl)
                .noFade()
                .into(issue_detail_avatar)
        issue_detail_timestamp.text = TimeAgo.using(ISO8601Utils.parse(issue.createDate, ParsePosition(0)).time)
        if (issue.state == "opened"){
            issue_detail_state.text = getString(R.string.opened).toUpperCase()
            issue_detail_state.setTextColor(Color.GREEN)
        } else {
            issue_detail_state.text = getString(R.string.closed).toUpperCase()
            issue_detail_state.setTextColor(Color.RED)
        }
//        with(issue_detail_body_view){
//            setShowLineNumbers(true)
//            setZoomSupportEnabled(true)
//            loadData(issue.body,"text/plain", Xml.Encoding.UTF_8.name)
//        }

    }

    override fun showComments(comments: List<Comment>) {
        issue_comments_recyclerview.adapter  = IssueCommentsAdapter(comments)
    }

    override fun showNetworkError() {

    }

    override fun hideNetworkError() {

    }

    override fun showLoginDialog() {

    }

    override fun openIssueInBrowser(htmlUrl: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl)))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
