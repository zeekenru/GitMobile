package com.kovapss.gitmobile.view.repositories.detail.content

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.delegateadapter.delegate.CompositeDelegateAdapter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.DelegatesAdapterModel
import com.kovapss.gitmobile.view.profile.UserProfileActivity
import com.kovapss.gitmobile.view.search.delegateAdapters.CommitsDelegateAdapter
import com.kovapss.gitmobile.view.search.delegateAdapters.UsersDelegateAdapter
import kotlinx.android.synthetic.main.fragment_repository_detail_content.*


class RepositoryDetailContentFragment : MvpAppCompatFragment(), RepositoryDetailContentView {

    @InjectPresenter
    lateinit var presenter: RepositoryDetailContentPresenter

    @ProvidePresenter
    fun providePresenter(): RepositoryDetailContentPresenter = with(checkNotNull(arguments)) {
        RepositoryDetailContentPresenter(getString(OWNER_LOGIN_KEY),
                getString(REPOSITORY_NAME_KEY), getInt(DATA_TYPE_KEY))
    }


    private val compositeAdapter = CompositeDelegateAdapter.Builder<DelegatesAdapterModel>()
            .add(CommitsDelegateAdapter({ presenter.commitClicked(it) }))
            .add(UsersDelegateAdapter({ presenter.contributorClicked(it) }))
            .build()


    companion object {
        const val OWNER_LOGIN_KEY = "owner_login_key"
        const val REPOSITORY_NAME_KEY = "repository_name_key"
        const val DATA_TYPE_KEY = "data_type_key"


        fun getInstance(ownerLogin: String, repositoryName: String, dataType: Int)
                : RepositoryDetailContentFragment {
            val fragment = RepositoryDetailContentFragment()
            val bundle = Bundle().apply {
                putString(OWNER_LOGIN_KEY, ownerLogin)
                putString(REPOSITORY_NAME_KEY, repositoryName)
                putInt(DATA_TYPE_KEY, dataType)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_repository_detail_content, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(repo_content_recyclerview) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = compositeAdapter
        }

    }

    override fun showData(data: List<DelegatesAdapterModel>) = compositeAdapter.swapData(data)

    override fun openUserDetailScreen(username: String) {
        val intent = Intent(context, UserProfileActivity::class.java).apply {
            putExtra(UserProfileActivity.USERNAME_KEY, username)
        }
        startActivity(intent)
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


}
