package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.HttpCodes
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.Comment
import com.kovapss.gitmobile.exceptions.NotAuthenticatedUserException
import com.kovapss.gitmobile.model.repositories.GistsRepository
import com.kovapss.gitmobile.model.system.PreferenceHelper
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import toothpick.Toothpick
import javax.inject.Inject


class GistDetailInteractor {

    @Inject
    lateinit var repository: GistsRepository

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }


    fun getComments(gistId: String): Single<List<Comment>>
            = repository.getGistComments("419219").subscribeOn(Schedulers.io())


    fun getShowLineNumbers(): Boolean = preferenceHelper.getShowLineNumbers()

    fun sendComment(githubId: String, commentText: String): Completable =
            Completable.fromAction { repository.createComment(githubId, commentText) }

    fun checkGistIsStarred(id : String) : Single<Boolean> {
        if (!preferenceHelper.getAuthStatus()) { throw NotAuthenticatedUserException() }
        else {
            return repository
                    .checkGistIsStarred(id, preferenceHelper.getAccessToken())
                    .subscribeOn(Schedulers.io())
                    .map { it.code().toString() == HttpCodes.CODE_204 }
        }

    }

    fun editComment(githubId: String, gistId: String, commentText: String) {

    }

    fun deleteComment(githubId: String, gistId: String) {

    }

    fun starGist(githubId: String) {
        if (!preferenceHelper.getAuthStatus()) { throw NotAuthenticatedUserException() }
        else {
            repository.starGist(githubId, preferenceHelper.getAccessToken())
        }

    }

}