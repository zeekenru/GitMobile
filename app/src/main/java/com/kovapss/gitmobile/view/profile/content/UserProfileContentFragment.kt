package com.kovapss.gitmobile.view.profile.content


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
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.view.gists.detail.GistDetailActivity
import com.kovapss.gitmobile.view.repositories.detail.RepositoryDetailActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.empty_result_placeholder.*
import kotlinx.android.synthetic.main.fragment_user_profile_content.*
import kotlinx.android.synthetic.main.internet_error_placeholder.*


class UserProfileContentFragment : MvpAppCompatFragment(), UserProfileContentView {

    @InjectPresenter
    lateinit var presenter: UserProfileContentPresenter

    @ProvidePresenter
    fun providePresenter(): UserProfileContentPresenter {
        return UserProfileContentPresenter(checkNotNull(arguments).getString(USERNAME_KEY),
                checkNotNull(arguments).getInt(DATA_TYPE))
    }

    private val delegateAdapter = CompositeDelegateAdapter.Builder<Any>()
            .add(GistsDelegateAdapter({ presenter.clickOnGist(it) }))
            .add(RepositoryDelegateAdapter({ presenter.clickOnRepository(it) }))
            .build()


    companion object {

        const val USERNAME_KEY = "username_key"

        const val DATA_TYPE = "data_type_key"

        const val REPOSITORY_DATA_TYPE = 0
        const val STARRED_DATA_TYPE = 1
        const val GISTS_DATA_TYPE = 2

        fun getInstance(bundle: Bundle): UserProfileContentFragment =
                UserProfileContentFragment().apply {
                    arguments = bundle
                }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_user_profile_content, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(user_profile_content_recycler_view) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = delegateAdapter
            hasFixedSize()
        }
        update_btn.setOnClickListener { presenter.networkErrorBtnClicked() }
        empty_result_update_btn.setOnClickListener { presenter.emptyResultErrorBtnCLicked() }

    }


    override fun showData(data: List<*>) {
        Logger.d("Showing content data: $data")
        delegateAdapter.swapData(data)
        Logger.d("Adapter itemCount: ${delegateAdapter.itemCount}")
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

    override fun openGistDetailScreen(gist: Gist) {
        val intent = Intent().apply {
            putExtra(GistDetailActivity.GIST_DETAIL, gist)
        }
        startActivity(intent)
    }

    override fun openRepositoryDetailScreen(repo: Repository) {
        val intent = Intent(context, RepositoryDetailActivity::class.java).apply {
            putExtra(RepositoryDetailActivity.REPOSITORY_KEY, repo)
        }
        startActivity(intent)
    }
}
