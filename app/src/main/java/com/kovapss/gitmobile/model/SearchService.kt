package com.kovapss.gitmobile.model

import retrofit2.http.GET
import retrofit2.http.Query


interface SearchService {

    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String, @Query("sort") sort: String, @Query("order") order: String)

    @GET("search/commits")
    fun searchCommits(@Query("q") query: String, @Query("sort") sort: String, @Query("order") order: String)

    @GET("search/code")
    fun searchCode(@Query("q") query: String, @Query("order") order: String)

    @GET("search/issues")
    fun searchIssues(@Query("q") query: String, @Query("sort") sort: String, @Query("order") order: String)

    @GET("search/users")
    fun searchUsers(@Query("q") query: String, @Query("sort") sort: String, @Query("order") order: String)
}