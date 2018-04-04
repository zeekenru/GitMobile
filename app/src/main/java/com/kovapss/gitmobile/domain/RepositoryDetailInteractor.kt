package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.HttpCodes
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.Comment
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.entities.commit.Commit
import com.kovapss.gitmobile.entities.issue.Issue
import com.kovapss.gitmobile.entities.repository.*
import com.kovapss.gitmobile.model.repositories.ReposRepository
import com.kovapss.gitmobile.model.system.PreferenceHelper
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import retrofit2.Response
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
            .map { it.body() }
            .subscribeOn(Schedulers.io())

    fun getRepositoryCommits(ownerLogin: String, repositoryName: String): Single<MutableList<Commit>> {
        return repository.getRepositoryCommits(ownerLogin, repositoryName)
                .map { it.body() }
                .map {
                    val commitsList = MutableList(it.size, { index -> it[index].commit })
                    commitsList
                }
                .subscribeOn(Schedulers.io())
    }

    fun getRepositoryIssues(ownerLogin: String, repositoryName: String): Single<List<Issue>> = repository.getRepositoryIssues(ownerLogin, repositoryName)
            .map { it.body() }
            .subscribeOn(Schedulers.io())

    fun getIssueComments(ownerLogin: String, repositoryName: String, issueNumber: String): Single<List<Comment>> = repository.getIssueComments(ownerLogin, repositoryName, issueNumber).map { it.body() }.subscribeOn(Schedulers.io())

    fun getRepositoryReadme(ownerLogin: String, repositoryName: String): Single<Readme> = repository.getRepositoryReadme(ownerLogin, repositoryName)
            .map { it.body() }
            .subscribeOn(Schedulers.io())

    fun getRepositoryTopics(ownerLogin: String, repositoryName: String): Single<Topics> = repository.getRepositoryTopics(ownerLogin, repositoryName)
            .subscribeOn(Schedulers.io())

    fun getRepositoryBranches(ownerLogin: String, repositoryName: String): Single<List<RepositoryBranch>> = repository.getRepositoryBranches(ownerLogin, repositoryName).subscribeOn(Schedulers.io())

    fun getRepositoryFiles(ownerLogin: String, repositoryName: String, path: String, branch: String)
            : Single<List<RepositoryFile>> = repository.getRepositoryFiles(ownerLogin, repositoryName, path, branch)
            .map { it.body() }
            .subscribeOn(Schedulers.io())

    fun getRepositoryFile(ownerLogin: String, repositoryName: String, path: String, branch: String)
            : Single<Response<RepositoryFile>> = repository.getRepositoryFIle(ownerLogin, repositoryName, path, branch)
            .subscribeOn(Schedulers.io())


    fun uploadFile(ownerLogin: String, repositoryName: String, file: CreateRepositoryFile): Completable = with(file) {
        repository.uploadFile(ownerLogin, repositoryName, path, message, content, branch, preferenceHelper.getAccessToken())
                .subscribeOn(Schedulers.io())
    }

    fun updateFile(ownerLogin: String, repositoryName: String, data: UpdateRepositoryFileData) = with(data) {
        repository.updateFile(ownerLogin, repositoryName, path, commitMessage, content, sha, branch, preferenceHelper.getAccessToken())
                .subscribeOn(AndroidSchedulers.mainThread())
    }


    fun getRepositoryStatus(ownerLogin: String, repositoryName: String): Single<RepositoryStatus> {

        val starredObservable =
                repository.checkRepositoryStarred(ownerLogin, repositoryName)
                        .map { it.code().toString() == HttpCodes.CODE_204 }
                        .onErrorReturn { t ->
                            t as HttpException
                            t.code().toString() == HttpCodes.CODE_204
                        }

        val subscriptionObservable = repository.checkRepositorySubscription(ownerLogin, repositoryName)
                .map { it.subscribed }
                .onErrorReturnItem(false)

        return Single.zip(starredObservable, subscriptionObservable, BiFunction<Boolean, Boolean, RepositoryStatus>
        { t1, t2 -> RepositoryStatus(t1, t2) })
                .subscribeOn(Schedulers.io())

    }

    fun starRepository(ownerLogin: String, repositoryName: String): Single<Boolean> =
            repository.starRepository(ownerLogin, repositoryName)
                    .subscribeOn(Schedulers.io())
                    .map { it.code().toString() == HttpCodes.CODE_204 }
                    .onErrorReturn { t ->
                        t as HttpException
                        t.code().toString() == HttpCodes.CODE_204
                    }

    fun unstarRepository(ownerLogin: String, repositoryName: String): Single<Boolean> =
            repository.unstarRepository(ownerLogin, repositoryName)
                    .subscribeOn(Schedulers.io())
                    .map { it.code().toString() == HttpCodes.CODE_204 }
                    .onErrorReturn { t ->
                        t as HttpException
                        t.code().toString() == HttpCodes.CODE_204
                    }

    fun setRepositorySubscription(ownerLogin: String, repositoryName: String, subscription: RepositorySubscription): Completable = repository.setRepositorySubscription(ownerLogin, repositoryName, subscription)
            .subscribeOn(Schedulers.io())
            .toCompletable()

    fun deleteRepositorySubscription(ownerLogin: String, repositoryName: String): Single<Boolean> =
            repository.deleteRepositorySubscription(ownerLogin, repositoryName)
                    .subscribeOn(Schedulers.io())
                    .map { it.code().toString() == HttpCodes.CODE_204 }
                    .onErrorReturn { t ->
                        t as HttpException
                        t.code().toString() == HttpCodes.CODE_204
                    }


    fun deleteRepository(ownerLogin: String, repositoryName: String): Completable {
        val single = repository.deleteRepository(ownerLogin, repositoryName)
                .map { it.code().toString() == HttpCodes.CODE_204 }
                .subscribeOn(Schedulers.io())
        return Completable.fromSingle(single)

    }

}