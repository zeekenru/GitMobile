package com.kovapss.gitmobile.view.repositories.detail.files


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.RepositoryFile
import com.kovapss.gitmobile.view.repositories.detail.files.createfile.CreateFileActivity
import com.kovapss.gitmobile.view.repositories.detail.files.viewer.FileViewerActivity
import kotlinx.android.synthetic.main.fragment_repository_files.*
import java.util.ArrayList


class RepositoryFilesFragment : MvpAppCompatFragment(), RepositoryFilesView {

    @InjectPresenter
    lateinit var presenter: RepositoryFilesPresenter

    @ProvidePresenter
    fun providePresenter() = with(checkNotNull(arguments)) {
        RepositoryFilesPresenter(getString(OWNER_LOGIN_KEY), getString(REPOSITORY_NAME_KEY))
    }

    private lateinit var branches: List<String>


    companion object {

        const val OWNER_LOGIN_KEY = "owner_login_key"
        const val REPOSITORY_NAME_KEY = "repository_name_key"


        fun getInstance(ownerLogin: String, repositoryName: String)
                : RepositoryFilesFragment {
            val fragment = RepositoryFilesFragment()
            val bundle = Bundle().apply {
                putString(OWNER_LOGIN_KEY, ownerLogin)
                putString(REPOSITORY_NAME_KEY, repositoryName)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_repository_files, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(repo_files_recyclerview){
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addOnScrollListener(object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    if (dy > 0)
                        repo_add_file_fab.hide()
                    else if (dy < 0)
                        repo_add_file_fab.show()
                }
            })
        }
        branches_spinner.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(adapter: AdapterView<*>, p1: View?, position: Int, p3 : Long) {
                presenter.onBranchItemSelected(adapter.getItemAtPosition(position) as String)
            }
        }

        repo_add_file_fab.setOnClickListener { presenter.addFileBtnClicked() }

    }

    override fun setBranches(branches: List<String>) {
        this.branches = branches
        val spinnerAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, branches)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        branches_spinner.adapter = spinnerAdapter
        repo_add_file_fab.visibility = View.VISIBLE
    }

    override fun showRepoContent(data: List<RepositoryFile>) {
        repo_files_recyclerview.adapter = RepositoryFilesAdapter(data, {presenter.filesItemClicked(it)}, {
            true
        })
    }

    override fun openFileViewer(file: RepositoryFile) {
        val intent = Intent(context, FileViewerActivity::class.java).apply {
            putExtra(FileViewerActivity.FILE_EXTRA_KEY, file)
            putStringArrayListExtra(FileViewerActivity.BRANCHES_KEY, branches as ArrayList<String>)
        }
        startActivity(intent)
    }

    override fun openCreateFileScreen(branches: List<String>) {
        val intent = Intent(context, CreateFileActivity::class.java).apply {
            putStringArrayListExtra(CreateFileActivity.BRANCHES_KEY, branches as ArrayList<String>)
        }

        startActivityForResult(intent, CreateFileActivity.CREATE_FILE_REQUEST_CODE )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED){
            if (requestCode == CreateFileActivity.CREATE_FILE_REQUEST_CODE){
                presenter.fileDataReceived(data.getParcelableExtra(CreateFileActivity.CREATE_FILE_DATA_KEY))
            }
        }
    }

    override fun showProgress() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.INVISIBLE
    }

    override fun showNetworkError() {
        network_error_placeholder.visibility = View.VISIBLE
    }

    override fun hideNetworkError() {
        network_error_placeholder.visibility = View.INVISIBLE
    }


}
