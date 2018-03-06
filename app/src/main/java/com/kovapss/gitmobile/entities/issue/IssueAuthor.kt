package com.kovapss.gitmobile.entities.issue

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IssueAuthor(@SerializedName("login") val login : String,
                       @SerializedName("avatar_url") val avatarUrl : String) : Parcelable