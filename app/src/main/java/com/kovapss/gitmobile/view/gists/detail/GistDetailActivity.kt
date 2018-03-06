package com.kovapss.gitmobile.view.gists.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.gson.internal.bind.util.ISO8601Utils
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.Comment
import com.orhanobut.logger.Logger
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_gist_detail.*
import kotlinx.android.synthetic.main.internet_error_placeholder.*
import java.text.ParsePosition


class GistDetailActivity : MvpAppCompatActivity(), GistDetailView {


    @InjectPresenter
    lateinit var presenter: GistDetailPresenter

    @ProvidePresenter
    fun providePresenter(): GistDetailPresenter = GistDetailPresenter(intent.getParcelableExtra(GIST_DETAIL))

    companion object {

        const val GIST_DETAIL = "gist_detail_key"

    }

    private lateinit var starMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("intent gist: ${intent.getParcelableExtra<Gist>(GIST_DETAIL)}")
        setContentView(R.layout.activity_gist_detail)
        setSupportActionBar(gist_detail_toolbar)
        gist_add_comment_send_btn.setOnClickListener {
            presenter.clickOnSendComment(gist_add_comment_edittext.text.toString())
        }
        checkNotNull(supportActionBar).apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.gist_detail_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        starMenuItem = menu.findItem(R.id.menu_star)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_star -> presenter.clickOnStar()
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun showComments(comments: List<Comment>) {
        Logger.d("show comments method, comments : ${comments.forEach { Logger.d(it) }}")
        with(gist_detail_comments_recyclerview) {
            layoutManager = LinearLayoutManager(context)
            adapter = GistCommentsListAdapter(comments)
        }
        no_comments_text.visibility = View.INVISIBLE
    }

    override fun hideComments() {
        gist_detail_comments_recyclerview.visibility = View.INVISIBLE
        no_comments_text.visibility = View.VISIBLE
    }


    override fun showGist(gist: Gist, showLineNumbers: Boolean) {
        with(gist) {
            Logger.d("DETAIL GIST : $gist")
            if (owner != null) {
                Picasso.with(this@GistDetailActivity)
                        .load(owner.avatarUrl)
                        .noFade()
                        .into(gist_detail_avatar)
                gist_login.text = owner.login
            } else {
                gist_login.text = getString(R.string.anonymous)
                Picasso.with(this@GistDetailActivity)
                        .load(R.drawable.default_avatar)
                        .noFade()
                        .into(gist_detail_avatar)
            }
            gist_timestamp.text = TimeAgo.using(ISO8601Utils.parse(createdDate, ParsePosition(0)).time)
            if (description.isNullOrEmpty()) {
                gist_detail_desctiption.text = getString(R.string.no_description)
            } else {
                gist_detail_desctiption.text = description
            }
            val adapter = GistDetailListAdapter(gist.files.values.toList(), { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_open_in_browser -> {
                        Logger.d("Click on popup menu item")
                        presenter.clickOnOpenInBrowser()
                        true
                    }
                    else -> { false }
                }
            }, showLineNumbers)

            gist_detail_recyclerview.adapter = adapter
            gist_detail_recyclerview.layoutManager = LinearLayoutManager(this@GistDetailActivity)

        }
    }


    override fun showInternetError() {
        network_error_placeholder.visibility = View.VISIBLE
        update_btn.setOnClickListener { presenter.clickOnUpdate() }
    }

    override fun showUndefinedError(msg: String) {

    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.INVISIBLE
        gist_detail_recyclerview.visibility = View.VISIBLE
//        gist_detail_comments_recyclerview.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun setGistStarredStatus(isStarred: Boolean) {
        if (isStarred) {starMenuItem.setIcon(R.drawable.ic_star_primary)}
        else { starMenuItem.setIcon(R.drawable.ic_star_border) }
    }

    override fun showLoginDialog() {
       AlertDialog.Builder(this)
               .setTitle(getString(R.string.need_to_login))
               .setMessage(getString(R.string.gist_login_dialog_msg))
               .setCancelable(true)
               .setPositiveButton(getString(R.string.sign_in)) { p0, p1 -> presenter.clickOnLogin()}
               .setNeutralButton(getString(R.string.no), { p0, _ -> p0.cancel() })
               .create()
               .show()
    }

    override fun openGistInBrowser(htmlUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(htmlUrl) }
        startActivity(intent)
    }
}