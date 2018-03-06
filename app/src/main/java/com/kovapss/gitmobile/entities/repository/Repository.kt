package com.kovapss.gitmobile.entities.repository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kovapss.gitmobile.entities.DelegatesAdapterModel
import com.kovapss.gitmobile.entities.Owner
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(
        @SerializedName("language")
        val language: String?,
        @SerializedName("id")
        val id: Int,
        @SerializedName("forks")
        val forks: Int,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("open_issues_count")
        val openIssuesCount: Int,
        @SerializedName("description")
        val description: String?,
        @SerializedName("watchers")
        val watchers: Int,
        @SerializedName("owner")
        val owner: Owner,
        @SerializedName("fork")
        val fork: Boolean,
        @SerializedName("open_issues")
        val openIssues: Int,
        @SerializedName("stargazers_count")
        val stargazersCount: Int,
        @SerializedName("updated_at")
        val updateDate : String,
        @SerializedName("forks_count")
        val forksCount: Int) : DelegatesAdapterModel, Parcelable
