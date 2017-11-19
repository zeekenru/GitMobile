package com.kovapss.gitmobile.entities

import com.google.gson.annotations.SerializedName

data class Repositories(
        @SerializedName("pushed_at")
        val pushedAt: String,
        @SerializedName("language")
        val language: String,
        @SerializedName("issue_comment_url")
        val issueCommentUrl: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("forks")
        val forks: Int,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("languages_url")
        val languagesUrl: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("private")
        val private: Boolean,
        @SerializedName("open_issues_count")
        val openIssuesCount: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("watchers")
        val watchers: Int,
        @SerializedName("owner")
        val owner: Owner,
        @SerializedName("fork")
        val fork: Boolean,
        @SerializedName("open_issues")
        val openIssues: Int,
        @SerializedName("watchers_count")
        val watchersCount: Int,
        @SerializedName("forks_count")
        val forksCount: Int)
