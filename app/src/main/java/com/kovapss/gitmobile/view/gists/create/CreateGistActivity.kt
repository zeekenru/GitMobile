package com.kovapss.gitmobile.view.gists.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.CreateGistData
import com.kovapss.gitmobile.entities.search.GistCreateFile
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_create_gist.*

class CreateGistActivity : MvpAppCompatActivity(), CreateGistView {

    @InjectPresenter
    lateinit var presenter: CreateGistPresenter

    companion object {
        const val CREATE_GIST_DATA_KEY = "create_gist_data"
        const val CREATE_GIST_REQUEST_CODE = 4
    }

    private val filesAdapter = GistFilesListAdapter(
            { presenter.onFileClick(it) },
            { presenter.onDeleteFileClick(it) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_gist)

        setSupportActionBar(gist_create_toolbar as Toolbar)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        gist_create_add_file_btn.setOnClickListener {
            gist_create_add_file_btn.isEnabled = false
            presenter.clickOnAddFileBtn()
        }

        with(gist_create_files_recyclerview) {
            layoutManager = LinearLayoutManager(this@CreateGistActivity,
                    LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
            adapter = filesAdapter
        }


    }

    override fun onResume() {
        super.onResume()
        gist_create_add_file_btn.isEnabled = true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_gist_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_done -> {
                val files = LinkedHashMap<String, GistCreateFile>()
                filesAdapter.data.forEach {
                    files[it.filename] = GistCreateFile(it.content)
                }
                setResult(Activity.RESULT_OK, Intent().putExtra(CREATE_GIST_DATA_KEY,
                        CreateGistData(gist_description_edit_text.text.toString(), files, true)))
                finish()
            }
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }



    override fun showCreateFileScreen() {
        startActivityForResult(Intent(this, CreateFileActivity::class.java),
                CreateFileActivity.CREATE_FILE_REQUEST_CODE)
    }

    override fun addFile(file: GistFile) {
        Logger.d("Add file method, item: $file")
        filesAdapter.addItem(file)
        gist_create_files_recyclerview.visibility = View.VISIBLE
    }

    override fun deleteItem(position: Int) {
        filesAdapter.removeItem(position)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CreateFileActivity.CREATE_FILE_REQUEST_CODE -> presenter.fileCreated(
                        data.getParcelableExtra(CreateFileActivity.FILE_DATA_INTENT_KEY))
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
