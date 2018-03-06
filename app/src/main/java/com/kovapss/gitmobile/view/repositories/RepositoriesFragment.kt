package com.kovapss.gitmobile.view.repositories


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.delegateadapter.delegate.CompositeDelegateAdapter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.view.profile.content.RepositoryDelegateAdapter
import com.kovapss.gitmobile.view.repositories.detail.RepositoryDetailActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_repositories.*


class RepositoriesFragment : MvpAppCompatFragment(), RepositoriesView {

   @InjectPresenter lateinit var presenter: RepositoriesPresenter

    private val reposAdapter = CompositeDelegateAdapter.Builder<Repository>()
            .add(RepositoryDelegateAdapter({presenter.repositoryClicked(it)}))
            .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
       =  inflater.inflate(R.layout.fragment_repositories, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(repos_recycler_view){
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = reposAdapter
        }
    }

    override fun showData(data: List<Repository>) {
        Logger.d("ShowData method, size: ${data.size}")
        reposAdapter.swapData(data)
    }

    override fun showProgress() {
      progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress_bar.visibility = View.INVISIBLE
    }

    override fun showNetworkError() {

    }

    override fun hideNetworkError() {

    }

    override fun showEmptyResultError() {

    }

    override fun hideEmptyResultError() {

    }

    override fun openRepositoryDetailScreen(repo: Repository) {
//        val intent = Intent(context, RepositoryDetailActivity::class.java).apply {
//            putExtra(RepositoryDetailActivity.REPOSITORY_KEY, repo)
//        }
//        startActivity(intent)
    }
}
