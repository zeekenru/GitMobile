package com.kovapss.gitmobile.view.gists


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kovapss.gitmobile.Constants.OAUTH_REQUEST_CODE
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.view.gists.create.CreateGistActivity
import com.kovapss.gitmobile.view.gists.detail.GistDetailActivity
import com.kovapss.gitmobile.view.login.LoginActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_gists.*
import kotlinx.android.synthetic.main.internet_error_placeholder.*


class GistsFragment : MvpAppCompatFragment(), GistsView {

    @InjectPresenter
    lateinit var presenter: GistsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_gists, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(gists_recycler_view) {
            layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
        }
        gists_fab.setOnClickListener { presenter.fabOnClick() }

        gist_bottom_nav_view.setOnNavigationItemSelectedListener {
            presenter.tabSelected(it.itemId)
            true
        }
    }

    override fun showGists(gists: List<Gist>) {
        gists_recycler_view.adapter = GistsListAdapter(gists, { presenter.itemOnClick(it) })
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.INVISIBLE
        gists_recycler_view.visibility = View.VISIBLE
    }

    override fun showInternetError() {
        network_error_placeholder.visibility = View.VISIBLE
        update_btn.setOnClickListener { presenter.clickOnUpdate() }
    }

    override fun showUndefinedError() {

    }

    override fun showNotAuthError() {
        AlertDialog.Builder(context!!)
                .setTitle(getString(R.string.need_to_login))
                .setMessage(getString(R.string.gists_login_dialog_msg))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.sign_in)) { _, _ -> presenter.clickOnLogin()}
                .setNeutralButton(getString(R.string.no), {
                    p0, _ -> p0.cancel()
                    gist_bottom_nav_view.selectedItemId = R.id.public_gists_item
                })
                .create()
                .show()
    }

    override fun showEmptyResultError() {
        empty_result_placeholder.visibility = View.VISIBLE
        gists_recycler_view.visibility = View.INVISIBLE
    }

    override fun openGistCreateView() {
        startActivity(Intent(context, CreateGistActivity::class.java))
    }

    override fun openDetailGist(gist: Gist) {
        Logger.d("Opening detail gist: $gist")
        val intent = Intent(context, GistDetailActivity::class.java).apply {
            putExtra(GistDetailActivity.GIST_DETAIL, gist)
        }
        startActivity(intent)
    }

    override fun openLoginDialogScreen() {
        val intent = Intent(context, LoginActivity::class.java)
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
}
