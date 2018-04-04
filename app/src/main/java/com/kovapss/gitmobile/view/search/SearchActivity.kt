package com.kovapss.gitmobile.view.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.delegateadapter.delegate.CompositeDelegateAdapter
import com.kovapss.gitmobile.FiltersDialogFragment
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.DelegatesAdapterModel
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.view.profile.UserProfileActivity
import com.kovapss.gitmobile.view.profile.content.RepositoryDelegateAdapter
import com.kovapss.gitmobile.view.repositories.detail.RepositoryDetailActivity
import com.kovapss.gitmobile.view.search.delegateAdapters.CommitsDelegateAdapter
import com.kovapss.gitmobile.view.search.delegateAdapters.IssuesDelegateAdapter
import com.kovapss.gitmobile.view.search.delegateAdapters.UsersDelegateAdapter
import kotlinx.android.synthetic.main.activity_search.*
import ru.alexbykov.nopaginate.paginate.Paginate
import ru.alexbykov.nopaginate.paginate.PaginateBuilder


class SearchActivity : MvpAppCompatActivity(), SearchView {

    @InjectPresenter lateinit var presenter: SearchPresenter

    private lateinit var contentSpinner: Spinner

    private lateinit var paginate: Paginate

    private val delegateAdapter = CompositeDelegateAdapter.Builder<DelegatesAdapterModel>()
            .add(RepositoryDelegateAdapter({presenter.repositoryClicked(it)}))
            .add(CommitsDelegateAdapter({presenter.commitClicked(it)}))
            .add(IssuesDelegateAdapter({presenter.issueClicked(it)}))
            .add(UsersDelegateAdapter({presenter.userClicked(it)}))
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(search_toolbar as Toolbar)
        initSpinners()
        with(checkNotNull(supportActionBar)){
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        with(search_recyclerview){
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = delegateAdapter
            paginate = PaginateBuilder()
                    .with(search_recyclerview)
                    .setOnLoadMoreListener { when(search_bottom_navview.selectedItemId){
                        R.id.search_menu_repos -> {presenter.reposItemSelected()}
                        R.id.search_menu_commits -> {presenter.commitsItemSelected()}
                        R.id.search_menu_issues -> {presenter.issuesItemSelected()}
                        R.id.search_menu_users -> {presenter.usersItemSelected()}
                    } }
                    .build()
        }
        search_bottom_navview.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.search_menu_repos -> {presenter.reposItemSelected()}
                R.id.search_menu_commits -> {presenter.commitsItemSelected()}
                R.id.search_menu_issues -> {presenter.issuesItemSelected()}
                R.id.search_menu_users -> {presenter.usersItemSelected()}
            }
            true
        }
        handleIntent(intent)
    }

    private fun initSpinners() {
//        val spinnerAdapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
//                R.array.content_types, android.R.layout.simple_spinner_item)
//        content_spinner.adapter = spinnerAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item != null) {
            when (item.itemId) {
                R.id.search_filters -> openFiltersDialog()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showProgress() { progress_bar.visibility = View.VISIBLE }

    override fun hideProgress() { progress_bar.visibility = View.INVISIBLE }

    override fun showSearchResults(results: List<DelegatesAdapterModel>) {
        delegateAdapter.swapData(results)
    }

    override fun openRepositoryDetailScreen(repository: Repository) {
        val intent = Intent(this, RepositoryDetailActivity::class.java).apply {
            putExtra(RepositoryDetailActivity.REPOSITORY_KEY, repository)
        }
        startActivity(intent)
    }

    override fun openUserProfileScreen(username : String) {
        val intent = Intent(this, UserProfileActivity::class.java).apply {
            putExtra(UserProfileActivity.USERNAME_KEY, username)
        }
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun openFiltersDialog() {
        val dialog = FiltersDialogFragment()
        val spinnerAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
                this, R.array.content_types, android.R.layout.simple_spinner_item)
            contentSpinner.adapter = spinnerAdapter
        AlertDialog.Builder(this)
                .setView(R.layout.filters_dialog_fragment)
                .create()
                .show()
    }

    private fun handleIntent(intent: Intent?) {
        if (intent != null && Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            checkNotNull(supportActionBar).title = query
            presenter.receivedQuery(query)
        }
    }
}

