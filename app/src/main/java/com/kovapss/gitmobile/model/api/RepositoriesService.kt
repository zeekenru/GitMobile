package com.kovapss.gitmobile.model.api

import com.kovapss.gitmobile.entities.Comment
import com.kovapss.gitmobile.entities.User
import com.kovapss.gitmobile.entities.commit.CommitResponse
import com.kovapss.gitmobile.entities.issue.Issue
import com.kovapss.gitmobile.entities.repository.*
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface RepositoriesService {

    @GET("users/{username}/repos")
    fun getUserRepositories(@Path("username") userName: String,
                            @Query("sort") sort: String = "full_name",
                            @Query("direction") direction: String = "desc")

    @GET("repositories")
    fun getPublicRepositories(): Single<retrofit2.Response<List<Repository>>>

    @GET("repos/{owner}/{repo}/contributors")
    fun getRepositoryContributors(@Path("owner") ownerLogin: String,
                                  @Path("repo") repositoryName: String): Single<retrofit2.Response<List<User>>>

    @GET("repos/{owner}/{repo}/commits")
    fun getRepositoryCommits(@Path("owner") ownerLogin: String,
                             @Path("repo") repositoryName: String): Single<retrofit2.Response<List<CommitResponse>>>

    @GET("repos/{owner}/{repo}/issues")
    fun getRepositoryIssues(@Path("owner") ownerLogin: String,
                            @Path("repo") repositoryName: String,
                            @Query("state") state: String = "all"): Single<retrofit2.Response<List<Issue>>>

    @GET("repos/{owner}/{repo}/issues/{number}/comments")
    fun getIssueComments(@Path("owner") login: String,
                         @Path("repo") repositoryName: String,
                         @Path("number") issueNumber: String): Single<retrofit2.Response<List<Comment>>>

    @GET("repos/{owner}/{repo}/readme")
    fun getRepositoryReadme(@Path("owner") ownerLogin: String,
                            @Path("repo") repositoryName: String): Single<retrofit2.Response<Readme>>

    @GET("user/starred/{owner}/{repo}")
    fun checkRepositoryStarred(@Path("owner") ownerLogin: String,
                               @Path("repo") repositoryName: String): Single<retrofit2.Response<ResponseBody>>

    @GET("/repos/{owner}/{repo}/subscription")
    fun checkRepositorySubscription(@Path("owner") ownerLogin: String,
                                    @Path("repo") repositoryName: String): Single<RepositorySubscription>

    @Headers("Accept: application/vnd.github.mercy-preview+json")
    @GET("repos/{owner}/{repo}/topics")
    fun getRepositoryTopics(@Path("owner") ownerLogin: String,
                            @Path("repo") repositoryName: String): Single<Topics>

    @GET("repos/{owner}/{repo}/contents/{path}")
    fun getRepositoryFiles(@Path("owner") ownerLogin: String,
                           @Path("repo") repositoryName: String,
                           @Path("path") path: String,
                           @Query("ref") branch: String): Single<Response<List<RepositoryFile>>>

    @GET("repos/{owner}/{repo}/contents/{path}")
    fun getRepositoryFile(@Path("owner") ownerLogin: String,
                          @Path("repo") repositoryName: String,
                          @Path("path") path: String,
                          @Query("ref") branch: String): Single<Response<RepositoryFile>>


    @GET("repos/{owner}/{repo}/branches")
    fun getRepositoryBranches(@Path("owner") ownerLogin: String,
                              @Path("repo") repositoryName: String): Single<List<RepositoryBranch>>


    @POST("user/repos")
    @Headers("Accept: application/json")
    fun createRepository(@Body model: CreateRepositoryModel): Single<Response<Repository>>

    @DELETE("/repos/{owner}/{repo}")
    fun deleteRepository(@Path("owner") ownerLogin: String,
                         @Path("repo") repositoryName: String): Single<Response<ResponseBody>>


    @PUT("user/starred/{owner}/{repo}")
    @Headers("Content-Length: 0")
    fun starRepository(@Path("owner") ownerLogin: String,
                       @Path("repo") repositoryName: String): Single<Response<ResponseBody>>


    @DELETE("user/starred/{owner}/{repo}")
    fun unstarRepository(@Path("owner") ownerLogin: String,
                         @Path("repo") repositoryName: String): Single<Response<ResponseBody>>

    @PUT("/repos/{owner}/{repo}/subscription")
    @Headers("Accept: application/json")
    fun setRepositorySubscription(@Path("owner") ownerLogin: String,
                                  @Path("repo") repositoryName: String,
                                  @Body subscription: RepositorySubscription): Single<Response<RepositorySubscription>>

    @DELETE("/repos/{owner}/{repo}/subscription")
    fun deleteRepositorySubscription(@Path("owner") ownerLogin: String,
                                     @Path("repo") repositoryName: String): Single<Response<ResponseBody>>

    @GET("/repos/{owner}/{repo}/collaborators")
    @Headers("Accept: application/vnd.github.hellcat-preview+json")
    fun getRepositoryCollaborators(@Path("owner") ownerLogin: String,
                                   @Path("repo") repositoryName: String,
                                   @Query("page") page : Int): Single<Response<List<Collaborator>>>

    @PUT("/repos/{owner}/{repo}/collaborators/{username}")
    @Headers("Content-Length: 0")
    fun addRepositoryCollaborator(@Path("owner") ownerLogin: String,
                                  @Path("repo") repositoryName: String,
                                  @Path("username") userName: String): Single<Response<ResponseBody>>

    @DELETE("/repos/{owner}/{repo}/collaborators/{username}")
    fun deleteRepositoryCollaborator(@Path("owner") ownerLogin: String,
                                     @Path("repo") repositoryName: String,
                                     @Path("username") userName: String): Single<Response<ResponseBody>>


    @PUT("repos/{owner}/{repo}/contents/{path}")
    fun updateFile(@Path("owner") ownerLogin: String,
                   @Path("repo") repositoryName: String,
                   @Path("path") path: String,
                   @Query("message") message: String,
                   @Query("content") content: String,
                   @Query("sha") sha: String,
                   @Query("branch") branch: String): Completable


    @PUT("repos/{owner}/{repo}/contents/{path}")
    fun uploadFile(@Path("owner") ownerLogin: String,
                   @Path("repo") repositoryName: String,
                   @Path("path") path: String,
                   @Query("message") message: String,
                   @Query("content") content: String,
                   @Query("branch") branch: String): Completable


}