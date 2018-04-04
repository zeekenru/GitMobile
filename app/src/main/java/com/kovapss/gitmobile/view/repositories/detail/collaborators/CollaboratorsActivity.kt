package com.kovapss.gitmobile.view.repositories.detail.collaborators

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kovapss.gitmobile.R
import com.kovapss.gitmobile.entities.repository.Collaborator
import com.kovapss.gitmobile.view.profile.UserProfileActivity
import kotlinx.android.synthetic.main.activity_collaborators.*
import ru.alexbykov.nopaginate.paginate.Paginate
import ru.alexbykov.nopaginate.paginate.PaginateBuilder

class CollaboratorsActivity : MvpAppCompatActivity(), CollaboratorsView {

    @InjectPresenter
    lateinit var presenter: CollaboratorsPresenter

    companion object {
        const val LOGIN_KEY = "login_key"
        const val REPOSITORY_NAME_KEY = "repo_name_key"
    }

    private lateinit var paginate: Paginate

    private lateinit var collaboratorsAdapter: CollaboratorsAdapter

    @ProvidePresenter
    fun providePresenter() = CollaboratorsPresenter(intent.getStringExtra(LOGIN_KEY),
            intent.getStringExtra(REPOSITORY_NAME_KEY))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collaborators)
        with(collaborators_recycler_view) {
            hasFixedSize()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        setSupportActionBar(collaborators_toolbar as Toolbar)

        checkNotNull(supportActionBar).apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.collaborators)
        }

        collaborators_fab.setOnClickListener { presenter.fabClicked() }
    }

    override fun showCollaborators(collaborators: List<Collaborator>) {
        collaboratorsAdapter = CollaboratorsAdapter(collaborators.toMutableList(),
                { presenter.itemClicked(it) }, { username, position -> presenter.deleteClicked(username, position) })
        collaborators_recycler_view.adapter = collaboratorsAdapter
        paginate = PaginateBuilder()
                .with(collaborators_recycler_view)
                .setOnLoadMoreListener { presenter.loadMore() }
                .build()
    }

    override fun openUserProfileScreen(username: String) {
        val intent = Intent(this, UserProfileActivity::class.java)
                .putExtra(UserProfileActivity.USERNAME_KEY, username)
        startActivity(intent)
    }

    override fun addCollaborators(collaborators: List<Collaborator>) {
        collaboratorsAdapter.addItems(collaborators)
    }

    override fun removeItem(position: Int) {
        collaboratorsAdapter.deleteItem(position)
    }

    override fun showDeleteDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.remove_collaborator)
                .setMessage(R.string.remove_collaborator_message)
                .setPositiveButton(getString(R.string.delete), { dialog, _ ->
                    presenter.deleteConfirmed()
                    dialog.cancel()
                })
                .setNegativeButton(R.string.cancel, {dialog, _ -> dialog.cancel() })
                .create()
                .show()
    }

    override fun showDeleteConfirm(deletedUsername: String) {
        Snackbar.make(collaborators_root_layout, "Collaborator $deletedUsername deleted", Snackbar.LENGTH_SHORT).show()
    }

    override fun showEmptyError() {
        collaborators_empty_error_view.visibility = View.VISIBLE
    }

    override fun showAddCollaboratorScreen() {
        AddCollaboratorFragment().show(supportFragmentManager, "tag")
    }

    override fun setNoMoreItems() {
        paginate.setNoMoreItems(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        paginate.unbind()
    }


}
