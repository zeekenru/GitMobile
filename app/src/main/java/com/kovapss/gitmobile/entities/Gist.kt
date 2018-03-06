package com.kovapss.gitmobile.entities


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gist(
        @SerializedName("id") val githubId: String,
        @SerializedName("description") val description: String?,
        @SerializedName("owner") val owner: Owner?,
        @SerializedName("created_at") val createdDate : String,
        @SerializedName("files") val files: LinkedHashMap<String, GistFile>,
        @SerializedName("public") val public : Boolean,
        @SerializedName("html_url") val htmlUrl : String,
        @SerializedName("comments") val comments: Int)  :  Parcelable
