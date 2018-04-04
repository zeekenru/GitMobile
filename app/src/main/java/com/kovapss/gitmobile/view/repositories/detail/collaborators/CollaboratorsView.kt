package com.kovapss.gitmobile.view.repositories.detail.collaborators

import com.arellomobile.mvp.MvpView
import com.kovapss.gitmobile.entities.repository.Collaborator


interface CollaboratorsView : MvpView {
    fun showCollaborators(collaborators: List<Collaborator>)
    fun openUserProfileScreen(username: String)
    fun addCollaborators(collaborators: List<Collaborator>)
    fun removeItem(position: Int)
    fun showDeleteDialog()
    fun showDeleteConfirm(deletedUsername: String)
    fun showEmptyError()
    fun showAddCollaboratorScreen()
    fun setNoMoreItems()
}