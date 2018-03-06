package com.kovapss.gitmobile.view.gists.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.kovapss.gitmobile.R
import kotlinx.android.synthetic.main.activity_create_gist.*

class CreateGistActivity : MvpAppCompatActivity(), CreateGistView {

    @InjectPresenter lateinit var presenter: CreateGistPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_gist)

        setSupportActionBar(gist_create_toolbar as Toolbar)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        gist_create_add_file_btn.setOnClickListener { presenter.clickOnAddFileBtn() }

        with(gist_create_files_recyclerview) {
            layoutManager = LinearLayoutManager(this@CreateGistActivity,
                    LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_gist_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_done -> presenter.onClickDoneMenuItem()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showGistVisibilityDialog() {

    }

    override fun showFileChooseDialog() {
        AlertDialog.Builder(this)
                .setTitle("Добавить файлы")
                .setItems(resources.getStringArray(R.array.choose_files)) { dialog, itemIndex ->
                    run {
                        presenter.selectedItem(itemIndex)
                        dialog.dismiss()
                    }
                }
                .setCancelable(true)
                .create()
                .show()
    }

    override fun showFiles(files: List<GistFile>) {
        with(gist_create_files_recyclerview){
            adapter = GistFilesListAdapter(files,
                    { presenter.onFileClick(it) },
                    { presenter.onDeleteFileClick(it) })
            visibility = View.VISIBLE
        }
    }

    override fun showCreateFileDialog() {
        startActivityForResult(Intent(this, CreateFileActivity::class.java),
                CreateFileActivity.CREATE_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CreateFileActivity.CREATE_FILE_REQUEST_CODE -> presenter.fileCreated(resultCode,
                        data.getParcelableExtra(CreateFileActivity.FILE_DATA_INTENT_KEY))
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun getFileFromSystem() {

    }
}
