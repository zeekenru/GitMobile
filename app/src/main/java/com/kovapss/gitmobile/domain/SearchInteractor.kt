package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.issue.Issue
import com.kovapss.gitmobile.entities.repository.Repository
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.entities.commit.Commit
import com.kovapss.gitmobile.model.repositories.SearchRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject


class SearchInteractor {

    @Inject
    lateinit var repository: SearchRepository

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun getRepositories(query: String): Single<List<Repository>> =
            repository.searchRepositories(query).subscribeOn(Schedulers.io())
                    .map { it.results }

    fun getCommits(query: String): Single<List<Commit>> =
            repository.searchCommits(query).subscribeOn(Schedulers.io())
                    .map { it.results }

    fun getIssues(query: String): Single<List<Issue>> = repository.searchIssues(query).subscribeOn(Schedulers.io())
            .map { it.results }

    fun getUsers(query: String) : Single<List<User>> = repository.searchUsers(query).subscribeOn(Schedulers.io())
            .map { it.results }
}