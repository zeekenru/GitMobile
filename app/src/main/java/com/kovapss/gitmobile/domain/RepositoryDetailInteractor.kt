package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.HttpCodes
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.Comment
import com.kovapss.gitmobile.entities.issue.Issue
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.entities.commit.Commit
import com.kovapss.gitmobile.entities.repository.*
import com.kovapss.gitmobile.model.repositories.ReposRepository
import com.kovapss.gitmobile.model.system.PreferenceHelper
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import toothpick.Toothpick
import javax.inject.Inject


class RepositoryDetailInteractor {

    @Inject
    lateinit var repository: ReposRepository

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun getPublicRepositories() = repository.getPublicRepositories()

    fun getRepositoryContributors(ownerLogin: String, repoName: String): Single<List<User>> = repository.getRepositoryContributors(ownerLogin, repoName)
            .subscribeOn(Schedulers.io())

    fun getRepositoryCommits(ownerLogin: String, repositoryName: String): Single<MutableList<Commit>> {
        return repository.getRepositoryCommits(ownerLogin, repositoryName)
                .map {
                    val commitsList = MutableList(it.size, { index -> it[index].commit })
                    commitsList
                }
                .subscribeOn(Schedulers.io())
    }

    fun getRepositoryIssues(ownerLogin: String, repositoryName: String): Single<List<Issue>> = repository.getRepositoryIssues(ownerLogin, repositoryName)
            .subscribeOn(Schedulers.io())

    fun getIssueComments(ownerLogin: String, repositoryName: String, issueNumber : String) : Single<List<Comment>>
    = repository.getIssueComments(ownerLogin, repositoryName, issueNumber).subscribeOn(Schedulers.io())

    fun getRepositoryReadme(ownerLogin: String, repositoryName: String): Single<Readme> = repository.getRepositoryReadme(ownerLogin, repositoryName)
            .subscribeOn(Schedulers.io())

    fun getRepositoryTopics(ownerLogin: String, repositoryName: String): Single<Topics> = repository.getRepositoryTopics(ownerLogin, repositoryName)
            .subscribeOn(Schedulers.io())

    fun getRepositoryBranches(ownerLogin: String, repositoryName: String): Single<List<RepositoryBranch>> = repository.getRepositoryBranches(ownerLogin, repositoryName).subscribeOn(Schedulers.io())

    fun getRepositoryFiles(ownerLogin: String, repositoryName: String, path: String, branch: String)
            : Single<List<RepositoryFile>> = repository.getRepositoryFiles(ownerLogin, repositoryName, path, branch)
            .subscribeOn(Schedulers.io())



    fun getRepositoryStatus(ownerLogin: String, repositoryName: String): Single<RepositoryStatus> {

        val starredObservable =
                repository.checkRepositoryStarred(ownerLogin, repositoryName, preferenceHelper.getAccessToken())
                        .map { true }
                        .onErrorReturn { t ->
                            t as HttpException
                            t.code().toString() == HttpCodes.CODE_204
                        }


        val subscriptionObservable = repository.checkRepositorySubscription(ownerLogin, repositoryName, "")
                .map { it.subscribed }
                .onErrorReturnItem(false)

        return Single.zip(starredObservable, subscriptionObservable, BiFunction<Boolean, Boolean, RepositoryStatus>
        { t1, t2 -> RepositoryStatus(t1, t2) })
                .subscribeOn(Schedulers.io())

    }

}