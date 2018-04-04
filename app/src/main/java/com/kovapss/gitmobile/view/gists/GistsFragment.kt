package com.kovapss.gitmobile.view.gists


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import ru.alexbykov.nopaginate.paginate.Paginate
import ru.alexbykov.nopaginate.paginate.PaginateBuilder


class GistsFragment : MvpAppCompatFragment(), GistsView {

    @InjectPresenter
    lateinit var presenter: GistsPresenter

    private lateinit var paginate: Paginate

    private lateinit var adapter: GistsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_gists, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(gists_recycler_view) {
            layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    if (dy > 0)
                        gists_fab.hide()
                    else if (dy < 0)
                        gists_fab.show()
                }
            })
        }
        gists_fab.setOnClickListener { presenter.fabOnClick() }

        gist_bottom_nav_view.setOnNavigationItemSelectedListener {
            presenter.tabSelected(it.itemId)
            empty_result_placeholder.visibility = View.INVISIBLE
            true
        }


    }

    override fun showGists(gists: List<Gist>) {
        adapter = GistsListAdapter(gists.toMutableList(), { presenter.itemOnClick(it) }).apply {
            gists_recycler_view.adapter = this
        }
        paginate = PaginateBuilder()
                .with(gists_recycler_view)
                .setOnLoadMoreListener { presenter.onLoadMore() }
                .build()
    }

    override fun addGists(gists: List<Gist>) = adapter.addItems(gists)


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
                .setPositiveButton(getString(R.string.sign_in)) { _, _ -> presenter.clickOnLogin() }
                .setNeutralButton(getString(R.string.no), { p0, _ ->
                    p0.cancel()
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
        startActivityForResult(Intent(context, CreateGistActivity::class.java),
                CreateGistActivity.CREATE_GIST_REQUEST_CODE)
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
        startActivityForResult(intent, LoginActivity.GIST_AUTH_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                LoginActivity.GIST_AUTH_CODE -> {
                    if (resultCode == Activity.RESULT_OK) {
                        presenter.loginSuccessful()
                    }
                }
                CreateGistActivity.CREATE_GIST_REQUEST_CODE -> {
                    if (data != null) {
                        if (data.hasExtra(CreateGistActivity.CREATE_GIST_DATA_KEY)) {
                            presenter.createGistDataReceived(data.getParcelableExtra(CreateGistActivity.CREATE_GIST_DATA_KEY))
                        }
                    }
                }
            }
        }
    }

    override fun setFabVisible(isVisible: Boolean) {
        if (isVisible){gists_fab.visibility = View.VISIBLE}
        else {gists_fab.visibility = View.INVISIBLE}
    }

    override fun onDestroy() {
        super.onDestroy()
        paginate.unbind()
    }
}
