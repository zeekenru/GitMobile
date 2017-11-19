package com.kovapss.gitmobile.entities


import com.google.gson.annotations.SerializedName

data class Gist(
        @SerializedName("id") val githubId: String,
        @SerializedName("description") val description: String?,
        @SerializedName("language") val language: String?,
        @SerializedName("owner") val owner: Owner?,
        @SerializedName("filename") val filename: String?,
        @SerializedName("public") val public : Boolean,
        @SerializedName("comments") val comments: Int)
