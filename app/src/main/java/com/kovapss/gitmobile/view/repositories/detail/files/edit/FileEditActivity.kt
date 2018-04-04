package com.kovapss.gitmobile.view.repositories.detail.files.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.UpdateRepositoryFileData
import com.kovapss.gitmobile.view.repositories.detail.files.createfile.CreateFileActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_edit.*

class FileEditActivity : MvpAppCompatActivity(), FileEditView {

    @InjectPresenter
    lateinit var presenter: RepositoryFileEditPresenter

    @ProvidePresenter
    fun providePresenter() = RepositoryFileEditPresenter(intent.getParcelableExtra(FILE_DATA_KEY),
            intent.getStringArrayListExtra(BRANCHES_KEY))

    private val cd = CompositeDisposable()

    private lateinit var doneItem: MenuItem

    companion object {
        const val FILE_DATA_KEY = "file_data_key"
        const val EDIT_FILE_REQUEST_CODE = 7
        const val EDIT_FILE_DATA_KEY = "edit_file_data_key"
        const val BRANCHES_KEY = "branches_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(repo_file_edit_toolbar as Toolbar)
        checkNotNull(supportActionBar).apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        cd.add(RxTextView.textChanges(repo_file_edit_msg_edittext)
                .skipInitialValue()
                .map { it.isNotEmpty() }
                .subscribe({ doneItem.isVisible = it }))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.file_edit_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        doneItem = menu.findItem(R.id.menu_done)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_done -> {
                presenter.doneClicked(edit_file_content_edit_text.text.toString(),
                        repo_file_edit_msg_edittext.text.toString(), edit_file_branches_spinner.selectedItemPosition)
            }
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun setContent(fileName: String, content: String, branches: ArrayList<String>) {
        edit_file_content_edit_text.setText(content, TextView.BufferType.EDITABLE)
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branches)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edit_file_branches_spinner.adapter = spinnerAdapter
        checkNotNull(supportActionBar).title = fileName
    }

    override fun setResult(data: UpdateRepositoryFileData) {
        setResult(Activity.RESULT_OK, Intent().putExtra(EDIT_FILE_DATA_KEY, data))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }
}
