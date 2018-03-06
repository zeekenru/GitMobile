package com.kovapss.gitmobile.view.repositories.detail.files.viewer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.RepositoryFile
import com.orhanobut.logger.Logger
import com.pddstudio.highlightjs.models.Theme
import kotlinx.android.synthetic.main.activity_file_viewer.*

class FileViewerActivity : MvpAppCompatActivity(), FileViewerView {

    @InjectPresenter
    lateinit var presenter: FileViewerPresenter

    @ProvidePresenter
    fun providePresenter() = with(checkNotNull(intent.getParcelableExtra<RepositoryFile>(FILE_EXTRA_KEY))) {
        FileViewerPresenter(this)
    }

    companion object {
        const val FILE_EXTRA_KEY = "file_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_viewer)
        with(repo_file_code_view) {
            setZoomSupportEnabled(true)
            setShowLineNumbers(true)
            theme = Theme.GITHUB
        }
        setSupportActionBar(file_viewer_toolbar as Toolbar)
        checkNotNull(supportActionBar).apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.file_viewer_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_viewer_open_in_browser -> presenter.openInBrowserItemClicked()
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun showFile(url: String) {
        Logger.d("url : `$url")
        repo_file_code_view.loadUrl(url)
    }

    override fun showFileName(name: String) {
        checkNotNull(supportActionBar).apply {
            title = name
        }
    }

    override fun openFileInBrowser(htmlUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl))
        startActivity(intent)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
