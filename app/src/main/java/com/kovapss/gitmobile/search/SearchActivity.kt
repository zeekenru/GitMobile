package com.kovapss.gitmobile.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.Spinner
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kovapss.gitmobile.FiltersDialogFragment
import com.kovapss.gitmobile.R
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : MvpAppCompatActivity(), SearchView {

    @InjectPresenter lateinit var presenter: SearchPresenter
    private lateinit var contentSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(search_toolbar as Toolbar)
        initSpinners()
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

    private fun openFiltersDialog() {
        val dialog = FiltersDialogFragment()
        val spinnerAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
                this, R.array.content_types, android.R.layout.simple_spinner_item)
        if (contentSpinner != null) {
            contentSpinner.adapter = spinnerAdapter
        } else Logger.d("Content spinner is null ")
        AlertDialog.Builder(this)
                .setView(R.layout.filters_dialog_fragment)
                .create()
                .show()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent != null && Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
        }
    }
}
