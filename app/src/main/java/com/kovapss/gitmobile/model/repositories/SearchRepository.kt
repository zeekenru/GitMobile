package com.kovapss.gitmobile.model.repositories

import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.search.CommitSearchResponse
import com.kovapss.gitmobile.entities.search.IssueSearchResponse
import com.kovapss.gitmobile.entities.search.RepositorySearchResponse
import com.kovapss.gitmobile.entities.search.UserSearchResponse
import com.kovapss.gitmobile.model.api.SearchService
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Query
import toothpick.Toothpick
import javax.inject.Inject


class SearchRepository {


    @Inject
    lateinit var apiService: SearchService

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun searchRepositories(@Query("q") query: String,
                           @Query("order") order: String = "desc") : Single<Response<RepositorySearchResponse>>
            = apiService.searchRepositories(query, order)


    fun searchCommits(@Query("q") query: String,
                      @Query("order") order: String = "desc") : Single<Response<CommitSearchResponse>>
            = apiService.searchCommits(query, order)


//    fun searchCode(@Query("q") query: String,
//                   @Query("order") order: String) = apiService.searchCode(query, order)


    fun searchIssues(@Query("q") query: String,
                     @Query("order") order: String = "desc") : Single<Response<IssueSearchResponse>>
            = apiService.searchIssues(query, order)


    fun searchUsers(@Query("q") query: String,
                    @Query("order") order: String = "desc") : Single<Response<UserSearchResponse>>
            = apiService.searchUsers(query, order)
}