package com.kovapss.gitmobile.view.repositories.detail.files.createfile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.CreateRepositoryFile
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_create_repo_file.*

class CreateFileActivity : AppCompatActivity() {

    companion object {
        const val BRANCHES_KEY = "branches"
        const val CREATE_FILE_DATA_KEY = "create_file"
        const val CREATE_FILE_REQUEST_CODE = 0x5
    }

    private lateinit var doneMenuItem: MenuItem

    private val cd = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_repo_file)
        setSupportActionBar(create_repo_file_toolbar as Toolbar)
        checkNotNull(supportActionBar).apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                intent.getStringArrayListExtra(BRANCHES_KEY))
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        create_file_branches_spinner.adapter = spinnerAdapter
        cd.add(RxTextView.textChanges(create_repo_file_msg_edittext)
                .skipInitialValue()
                .isEmpty
                .doOnSuccess { doneMenuItem.isVisible = it == false }
                .subscribe())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.create_repo_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_done).apply {
            doneMenuItem = this
            isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_done -> {
                val branch = intent.getStringArrayListExtra(BRANCHES_KEY)[create_file_branches_spinner.selectedItemPosition]
                val data = CreateRepositoryFile("", create_repo_file_msg_edittext.text.toString(),
                        toBase64(create_repo_file_content_edittext.text.toString()), branch)
                Logger.d("createRepoFile, data: $data")
                setResult(Activity.RESULT_OK, Intent().putExtra(CREATE_FILE_DATA_KEY, data))
            }
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun toBase64(text : String) : String = Base64.encodeToString(text.toByteArray(), Base64.DEFAULT)

    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }


}
