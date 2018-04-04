package com.kovapss.gitmobile.model.repositories

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.entities.commit.CommitResponse
import com.kovapss.gitmobile.entities.issue.Issue
import com.kovapss.gitmobile.entities.repository.*
import com.kovapss.gitmobile.model.api.RepositoriesService
import io.reactivex.Single
import retrofit2.Response
import toothpick.Toothpick
import javax.inject.Inject


class ReposRepository {

    @Inject
    lateinit var api: RepositoriesService

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }


    fun createRepository(createRepositoryModel: CreateRepositoryModel)
            : Single<retrofit2.Response<Repository>> =
            api.createRepository(createRepositoryModel)


    fun getPublicRepositories(): Single<retrofit2.Response<List<Repository>>> = api.getPublicRepositories()

    fun getUserRepostories(username: String, sort: String, direction: String) = api.getUserRepositories(username, sort, direction)

    fun getRepositoryContributors(ownerLogin: String, repoName: String): Single<retrofit2.Response<List<User>>> = api.getRepositoryContributors(ownerLogin, repoName)

    fun getRepositoryCommits(ownerLogin: String, repositoryName: String): Single<retrofit2.Response<List<CommitResponse>>> = api.getRepositoryCommits(ownerLogin, repositoryName)

    fun getRepositoryIssues(ownerLogin: String, repositoryName: String): Single<retrofit2.Response<List<Issue>>> = api.getRepositoryIssues(ownerLogin, repositoryName)

    fun getIssueComments(ownerLogin: String, repositoryName: String, issueNumber: String) = api.getIssueComments(ownerLogin, repositoryName, issueNumber)

    fun getRepositoryReadme(ownerLogin: String, repositoryName: String): Single<retrofit2.Response<Readme>> = api.getRepositoryReadme(ownerLogin, repositoryName)

    fun checkRepositoryStarred(ownerLogin: String, repositoryName: String) = api.checkRepositoryStarred(ownerLogin, repositoryName)

    fun starRepository(ownerLogin: String, repositoryName: String) = api.starRepository(ownerLogin, repositoryName)

    fun unstarRepository(ownerLogin: String, repositoryName: String) = api.unstarRepository(ownerLogin, repositoryName)

    fun setRepositorySubscription(ownerLogin: String, repositoryName: String, subscription: RepositorySubscription)
            = api.setRepositorySubscription(ownerLogin, repositoryName, subscription)

    fun deleteRepositorySubscription(ownerLogin: String, repositoryName: String) =
            api.deleteRepositorySubscription(ownerLogin, repositoryName)

    fun checkRepositorySubscription(ownerLogin: String, repositoryName: String)
            : Single<RepositorySubscription> = api.checkRepositorySubscription(ownerLogin, repositoryName)

    fun getRepositoryTopics(ownerLogin: String, repositoryName: String): Single<Topics> = api.getRepositoryTopics(ownerLogin, repositoryName)

    fun getRepositoryFiles(ownerLogin: String, repositoryName: String, path: String, branch: String)
            : Single<retrofit2.Response<List<RepositoryFile>>> = api.getRepositoryFiles(ownerLogin, repositoryName, path, branch)

    fun getRepositoryFIle(ownerLogin: String, repositoryName: String, path: String, branch: String)
            : Single<Response<RepositoryFile>> = api.getRepositoryFile(ownerLogin, repositoryName, path, branch)

    fun getRepositoryBranches(ownerLogin: String, repositoryName: String): Single<List<RepositoryBranch>> = api.getRepositoryBranches(ownerLogin, repositoryName)

    fun uploadFile(ownerLogin: String, repositoryName: String, path: String,
                   message: String, content: String, branch: String, accessToken: String) = api.uploadFile(ownerLogin, repositoryName, path, message, content, branch)

    fun updateFile(ownerLogin: String, repositoryName: String, path: String,
                   message: String, content: String, sha: String, branch: String, accessToken: String) = api.updateFile(ownerLogin, repositoryName, path, message, content, sha, branch)

    fun deleteRepository(ownerLogin : String, repositoryName: String) = api.deleteRepository(ownerLogin, repositoryName)

}