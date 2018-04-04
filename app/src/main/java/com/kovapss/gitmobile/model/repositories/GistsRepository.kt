package com.kovapss.gitmobile.model.repositories

import com.kovapss.gitmobile.Scopes.APP_SCOPE
import com.kovapss.gitmobile.Scopes.NETWORK_SCOPE
import com.kovapss.gitmobile.entities.Comment
import com.kovapss.gitmobile.entities.CreateGistData
import com.kovapss.gitmobile.entities.Gist
import com.kovapss.gitmobile.exceptions.NotAuthenticatedUserException
import com.kovapss.gitmobile.model.api.GistsService
import com.kovapss.gitmobile.model.system.PreferenceHelper
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
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


    fun getUserGists(userName: String, page : Int): Single<Response<List<Gist>>> = apiService.getUserGists(userName, page)


    fun getPublicGists(page: Int): Single<Response<List<Gist>>> = apiService.getPublicGists(page)


    fun getStarredGists(page: Int): Single<Response<List<Gist>>> {
        if (!preferenceHelper.getAuthStatus()) {
            throw NotAuthenticatedUserException()
        }
        return apiService.getStarredGists(page)
    }

    fun checkGistIsStarred(id: String): Single<Response<ResponseBody>>  {
        if (!preferenceHelper.getAuthStatus()) {
            throw NotAuthenticatedUserException()
        }
        return apiService.checkGistStar(id)
    }

    fun getGist(id: String) = apiService.getGist(id)


    fun createGist(data : CreateGistData) : Single<Response<Gist>>
            = apiService.createGist(data)


    fun changeGist(id: String,
                   description: String,
                   files: Any,
                   content: String,
                   filename: String) {
        apiService.changeGist(id, description, files, content, filename)
    }


    fun getGistCommit(id: String) = apiService.getGistCommit(id)

    fun getGistComments(id: String): Single<Response<List<Comment>>> = apiService.getGistComments(id)

    fun createComment(id: String, commentBody: String) = apiService.createCommit(id, commentBody)


    fun starGist(id: String) = apiService.starGist(id)


    fun unstarGist(id: String) = apiService.unstarGist(id)


    fun deleteGist(id: String) = apiService.deleteGist(id)
}

