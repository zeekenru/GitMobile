package com.kovapss.gitmobile.model.repositories

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.*
import com.kovapss.gitmobile.entities.commit.CommitResponse
import com.kovapss.gitmobile.entities.issue.Issue
import com.kovapss.gitmobile.entities.repository.*
import com.kovapss.gitmobile.model.api.RepositoriesService
import io.reactivex.Single
import toothpick.Toothpick
import javax.inject.Inject


class ReposRepository {

    @Inject
    lateinit var api: RepositoriesService

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun getPublicRepositories(): Single<List<Repository>> = api.getPublicRepositories()

    fun getUserRepostories(username: String, sort: String, direction: String)
            = api.getUserRepositories(username, sort, direction)

    fun getRepositoryContributors(ownerLogin: String, repoName: String): Single<List<User>>
            = api.getRepositoryContributors(ownerLogin, repoName)

    fun getRepositoryCommits(ownerLogin: String, repositoryName: String): Single<List<CommitResponse>>
            = api.getRepositoryCommits(ownerLogin, repositoryName)

    fun getRepositoryIssues(ownerLogin: String, repositoryName: String): Single<List<Issue>>
            = api.getRepositoryIssues(ownerLogin, repositoryName)
    fun getIssueComments(ownerLogin: String, repositoryName: String, issueNumber : String)
            = api.getIssueComments(ownerLogin, repositoryName, issueNumber)

    fun getRepositoryReadme(ownerLogin: String, repositoryName: String): Single<Readme>
            = api.getRepositoryReadme(ownerLogin, repositoryName)

    fun checkRepositoryStarred(ownerLogin: String, repositoryName: String, accessToken: String)
            = api.checkRepositoryStarred(ownerLogin, repositoryName, accessToken)

    fun checkRepositorySubscription(ownerLogin: String, repositoryName: String, accessToken: String) : Single<RepositorySubscription>
    = api.checkRepositoryWatched(ownerLogin, repositoryName, accessToken)

    fun getRepositoryTopics(ownerLogin: String, repositoryName: String): Single<Topics>
            = api.getRepositoryTopics(ownerLogin, repositoryName)

    fun getRepositoryFiles(ownerLogin: String, repositoryName: String, path : String,  branch: String): Single<List<RepositoryFile>>
            = api.getRepositoryFiles(ownerLogin, repositoryName, path, branch)

    fun getRepositoryBranches(ownerLogin: String, repositoryName: String): Single<List<RepositoryBranch>>
            = api.getRepositoryBranches(ownerLogin, repositoryName)


}