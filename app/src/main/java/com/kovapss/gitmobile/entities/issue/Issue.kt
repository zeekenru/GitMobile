package com.kovapss.gitmobile.entities.issue

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kovapss.gitmobile.entities.DelegatesAdapterModel
import com.kovapss.gitmobile.entities.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Issue (@SerializedName("title") val title : String,
                  @SerializedName("user") val author : IssueAuthor,
                  @SerializedName("number") val number : String,
                  @SerializedName("html_url") val htmlUrl : String,
                  @SerializedName("state") val state : String,
                  @SerializedName("created_at") val createDate : String,
                  @SerializedName("comments") val commentCount : Int,
                  @SerializedName("body") val body : String) : DelegatesAdapterModel, Parcelable