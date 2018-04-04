package com.kovapss.gitmobile.model.repositories

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.model.api.RepositoriesService
import com.kovapss.gitmobile.model.api.SearchService
import toothpick.Toothpick
import javax.inject.Inject


class CollaboratorsRepository {

    @Inject lateinit var repositoriesService : RepositoriesService

    @Inject lateinit var searchService : SearchService

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }


    fun getRepositoryCollaborators(ownerLogin : String, repositoryName : String, page : Int)
            = repositoriesService.getRepositoryCollaborators(ownerLogin, repositoryName, page)

    fun findUsers(query : String) = searchService.searchUsers(query, "desc")

    fun addRepositoryCollaborator(ownerLogin : String, repositoryName : String, username : String)
            = repositoriesService.addRepositoryCollaborator(ownerLogin, repositoryName, username)

    fun deleteRepositoryCollaborator(ownerLogin : String, repositoryName : String, username : String)
            = repositoriesService.deleteRepositoryCollaborator(ownerLogin, repositoryName, username)
}