package com.kovapss.gitmobile.entities.repository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateRepositoryModel(@SerializedName("name") val name : String,
                                 @SerializedName("description") val description : String,
                                 @SerializedName("homepage") val homepage : String,
                                 @SerializedName("has_issues") val hasIssues : Boolean,
                                 @SerializedName("has_wiki") val hasWiki : Boolean) : Parcelable