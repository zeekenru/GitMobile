package com.kovapss.gitmobile.model.repositories

import com.kovapss.gitmobile.Scopes.APP_SCOPE
import com.kovapss.gitmobile.Scopes.NETWORK_SCOPE
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.entities.Comment
import com.kovapss.gitmobile.exceptions.NotAuthenticatedUserException
import com.kovapss.gitmobile.model.api.GistsService
import com.kovapss.gitmobile.model.system.PreferenceHelper
import io.reactivex.Single
import okhttp3.Response
import toothpick.Toothpick
import javax.inject.Inject


class GistsRepository {

    @Inject
    lateinit var apiService: GistsService

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    init {
        Toothpick.inject(this, Toothpick.openScopes(APP_SCOPE, NETWORK_SCOPE))
    }


    fun getUserGists(userName: String): Single<List<Gist>> = apiService.getUserGists(userName)


    fun getPublicGists(): Single<List<Gist>> = apiService.getPublicGists()


    fun getStarredGists(accessToken : String): Single<List<Gist>> {
        if (!preferenceHelper.getAuthStatus()) {
            throw NotAuthenticatedUserException()
        }
        return apiService.getStarredGists(accessToken)
    }

    fun checkGistIsStarred(id: String, accessToken : String): Single<Response>  {
        if (!preferenceHelper.getAuthStatus()) {
            throw NotAuthenticatedUserException()
        }
        return apiService.checkGistStar(id, accessToken)
    }

    fun getGist(id: String) = apiService.getGist(id)


    fun createGist(files: Any, description: String, public: Boolean)
            = apiService.createGist(files, description, public = true)


    fun changeGist(id: String,
                   description: String,
                   files: Any,
                   content: String,
                   filename: String) {
        apiService.changeGist(id, description, files, content, filename)
    }


    fun getGistCommit(id: String) = apiService.getGistCommit(id)

    fun getGistComments(id: String): Single<List<Comment>> = apiService.getGistComments(id)

    fun createComment(id: String, commentBody: String) = apiService.createCommit(id, commentBody)


    fun starGist(id: String, accessToken: String) = apiService.starGist(id, accessToken)


    fun unstarGist(id: String) = apiService.unstarGist(id)


    fun deleteGist(id: String) = apiService.deleteGist(id)
}

