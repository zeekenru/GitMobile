package com.kovapss.gitmobile.entities

import com.google.gson.annotations.SerializedName

data class Owner(
        @SerializedName("login")
        val login: String?,
        @SerializedName("type")
        val type: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("avatar_url")
        val avatarUrl: String?,
        @SerializedName("id")
        val id: Int)