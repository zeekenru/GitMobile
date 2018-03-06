package com.kovapss.gitmobile.entities.repository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryFile(@SerializedName("name") val name : String,
                          @SerializedName("path") val path : String,
                          @SerializedName("html_url") val htmlUrl : String,
                          @SerializedName("download_url") val url : String?,
                          @SerializedName("type") val type : String) : Parcelable