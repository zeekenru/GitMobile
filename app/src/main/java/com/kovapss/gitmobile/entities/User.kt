package com.kovapss.gitmobile.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(@SerializedName("login") val login: String,
                @SerializedName("id") val id: Long,
                @SerializedName("avatar_url") val avatarUrl: String,
                @SerializedName("name") val name: String,
                @SerializedName("company") val company: String?,
                @SerializedName("blog") val blogUrl: String?,
                @SerializedName("location") val location: String?,
                @SerializedName("email") val email: String?,
                @SerializedName("bio") val bio: String?,
                @SerializedName("public_repos") val publicRepos: String,
                @SerializedName("public_gists") val publicGists: String,
                @SerializedName("followers") val followers: String,
                @SerializedName("following") val following: String) : Parcelable, DelegatesAdapterModel

