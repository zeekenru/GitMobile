package com.kovapss.gitmobile.model.api

import com.kovapss.gitmobile.entities.search.CommitSearchResponse
import com.kovapss.gitmobile.entities.search.IssueSearchResponse
import com.kovapss.gitmobile.entities.search.RepositorySearchResponse
import com.kovapss.gitmobile.entities.search.UserSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface SearchService {

    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String, @Query("order") order: String) : Single<RepositorySearchResponse>

    @GET("search/commits")
    @Headers("Accept: application/vnd.github.cloak-preview")
    fun searchCommits(@Query("q") query: String, @Query("order") order: String) : Single<CommitSearchResponse>

//    @GET("search/code")
//    fun searchCode(@Query("q") query: String, @Query("order") order: String)

    @GET("search/issues")
    fun searchIssues(@Query("q") query: String, @Query("order") order: String) : Single<IssueSearchResponse>

    @GET("search/users")
    fun searchUsers(@Query("q") query: String, @Query("order") order: String) : Single<UserSearchResponse>
}