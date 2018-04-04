package com.kovapss.gitmobile.view.repositories.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.CreateRepositoryModel
import kotlinx.android.synthetic.main.activity_create_repository.*

class CreateRepositoryActivity : AppCompatActivity() {


    private lateinit var doneMenuItem : MenuItem

    companion object {
        const val GET_REPO_DATA_REQUEST_CODE = 3
        const val REPO_DATA_EXTRA_KEY = "repo_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_repository)
        setSupportActionBar(create_repo_toolbar as Toolbar)
        checkNotNull(supportActionBar).apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        create_repo_name_edittext.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (p0.isEmpty()) {
                    create_repo_name_input.error = "Must be specified"
                    doneMenuItem.isVisible = false
                } else{
                    create_repo_name_input.isErrorEnabled = false
                    doneMenuItem.isVisible = true
                }

            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.create_repo_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        doneMenuItem = menu.findItem(R.id.menu_done)
        doneMenuItem.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> onBackPressed()
            R.id.menu_done -> {
                val data = CreateRepositoryModel(create_repo_name_edittext.text.toString(),
                        create_repo_description_edittext.text.toString(),
                        create_repo_homepage_edittext.text.toString(),
                        create_repo_issue_checkbox.isChecked,
                        create_repo_wiki_checkbox.isChecked)
                setResult(Activity.RESULT_OK, Intent().putExtra(REPO_DATA_EXTRA_KEY, data))
                finish()
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
