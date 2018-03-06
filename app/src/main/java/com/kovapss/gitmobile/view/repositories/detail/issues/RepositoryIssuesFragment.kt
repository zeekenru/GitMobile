package com.kovapss.gitmobile.view.repositories.detail.issues


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.delegateadapter.delegate.CompositeDelegateAdapter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.issue.Issue
import com.kovapss.gitmobile.view.repositories.detail.issues.detail.IssueDetailActivity
import com.kovapss.gitmobile.view.search.delegateAdapters.IssuesDelegateAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_repository_issues.*


class RepositoryIssuesFragment : MvpAppCompatFragment(), RepositoryIssuesView {


    @InjectPresenter
    lateinit var presenter: RepositoryIssuesPresenter

    private val issueAdapter = CompositeDelegateAdapter.Builder<Issue>()
            .add(IssuesDelegateAdapter({ presenter.clickOnIssue(it) }))
            .build()

    @ProvidePresenter
    fun providePresenter(): RepositoryIssuesPresenter = with(checkNotNull(arguments)) {
        Logger.d("arguments is null: ${arguments == null}")
        RepositoryIssuesPresenter(getString(OWNER_LOGIN_KEY), getString(REPOSITORY_NAME_KEY))
    }


    companion object {
        const val OWNER_LOGIN_KEY = "owner_login_key"
        const val REPOSITORY_NAME_KEY = "repository_name_key"


        fun getInstance(ownerLogin: String, repositoryName: String): RepositoryIssuesFragment {
            val fragment = RepositoryIssuesFragment()
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
            = inflater.inflate(R.layout.fragment_repository_issues, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(repo_issues_recyclerview) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = issueAdapter
        }
        repo_issues_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.tag) {
                    getString(R.string.repo_opened_issues_tab) -> {
                    }
                    getString(R.string.repo_closed_issue_tag) -> {
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showData(data: List<Issue>) {
        issueAdapter.swapData(data)
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

    override fun showEmptyResultError() {
        empty_result_placeholder.visibility = View.VISIBLE
    }

    override fun hideEmptyResultError() {
        empty_result_placeholder.visibility = View.INVISIBLE
    }

    override fun openIssueDetailScreen(issue: Issue, ownerLogin : String, repositoryName: String) {
        val intent = Intent(context, IssueDetailActivity::class.java).apply {
            putExtra(IssueDetailActivity.ISSUE_KEY, issue)
            putExtra(IssueDetailActivity.REPO_OWNER_LOGIN_KEY, ownerLogin)
            putExtra(IssueDetailActivity.REPOSITORY_NAME_KEY, repositoryName)
        }
        startActivity(intent)
    }
}
