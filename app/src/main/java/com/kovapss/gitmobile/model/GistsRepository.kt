package com.kovapss.gitmobile.model

import com.kovapss.gitmobile.Scopes.APP_SCOPE
import com.kovapss.gitmobile.Scopes.NETWORK_SCOPE
import com.kovapss.gitmobile.entities.Gist
import io.reactivex.Observable
import toothpick.Toothpick
import javax.inject.Inject


class GistsRepository {

    @Inject lateinit var apiService: GistsService

    init {
        Toothpick.inject(this, Toothpick.openScopes(APP_SCOPE, NETWORK_SCOPE))
    }


    fun getUserGists(userName: String): Observable<Gist> = apiService.getUserGists(userName)


    fun getPublicGists(): Observable<List<Gist>> = apiService.getPublicGists()


    fun getStarredGists(): Observable<Gist> = apiService.getStarredGists()


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

    fun getGistComments(id: String) = apiService.getGistComments(id)


    fun starGist(id: String) = apiService.starGist(id)


    fun unstarGist(id: String) = apiService.unstarGist(id)


    fun deleteGist(id: String) = apiService.deleteGist(id)

}