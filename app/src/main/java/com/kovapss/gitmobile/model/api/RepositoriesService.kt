package com.kovapss.gitmobile.model.api

import com.kovapss.gitmobile.entities.*
import com.kovapss.gitmobile.entities.commit.CommitResponse
import com.kovapss.gitmobile.entities.issue.Issue
import com.kovapss.gitmobile.entities.repository.*
import io.reactivex.Single
import okhttp3.Response
import retrofit2.http.*


interface RepositoriesService {

    @GET("users/{username}/repos")
    fun getUserRepositories(@Path("username") userName: String,
                            @Query("sort") sort: String = "full_name",
                            @Query("direction") direction: String = "desc")

    @GET("repositories")
    fun getPublicRepositories() : Single<List<Repository>>

    @GET("repos/{owner}/{repo}/contributors")
    fun getRepositoryContributors(@Path("owner") ownerLogin: String,
                                  @Path("repo") repositoryName: String): Single<List<User>>

    @GET("repos/{owner}/{repo}/commits")
    fun getRepositoryCommits(@Path("owner") ownerLogin: String,
                             @Path("repo") repositoryName: String): Single<List<CommitResponse>>

    @GET("repos/{owner}/{repo}/issues")
    fun getRepositoryIssues(@Path("owner") ownerLogin: String,
                            @Path("repo") repositoryName: String,
                            @Query("state") state: String = "all"): Single<List<Issue>>

    @GET("repos/{owner}/{repo}/issues/{number}/comments")
    fun getIssueComments(@Path("owner") login : String,
                        @Path("repo") repositoryName: String,
                        @Path("number") issueNumber : String) : Single<List<Comment>>

    @GET("repos/{owner}/{repo}/readme")
    fun getRepositoryReadme(@Path("owner") ownerLogin: String,
                            @Path("repo") repositoryName: String): Single<Readme>

    @GET("user/starred/{owner}/{repo}")
    fun checkRepositoryStarred(@Path("owner") ownerLogin: String,
                               @Path("repo") repositoryName: String,
                               @Query("access_token") accessToken : String) : Single<Response>

    @GET("/repos/{owner}/{repo}/subscription")
    fun checkRepositoryWatched(@Path("owner") ownerLogin: String,
                               @Path("repo") repositoryName: String,
                               @Query("access_token") accessToken : String) : Single<RepositorySubscription>

    @Headers("Accept: application/vnd.github.mercy-preview+json")
    @GET("repos/{owner}/{repo}/topics")
    fun getRepositoryTopics(@Path("owner") ownerLogin: String,
                            @Path("repo") repositoryName: String) : Single<Topics>

    @GET("repos/{owner}/{repo}/contents/{path}")
    fun getRepositoryFiles(@Path("owner") ownerLogin: String,
                           @Path("repo") repositoryName: String,
                           @Path("path") path : String,
                           @Query("ref") branch : String) : Single<List<RepositoryFile>>


    @GET("repos/{owner}/{repo}/branches")
    fun getRepositoryBranches(@Path("owner") ownerLogin: String,
                              @Path("repo") repositoryName: String) : Single<List<RepositoryBranch>>

    @PUT("user/starred/{owner}/{repo}")
    fun starRepository(@Path("owner") ownerLogin: String,
                       @Path("repo") repositoryName: String,
                       @Query("access_token") accessToken : String)


    @DELETE("user/starred/{owner}/{repo}")
    fun unstarRepository(@Path("owner") ownerLogin: String,
                         @Path("repo") repositoryName: String,
                         @Query("access_token") accessToken : String)






}