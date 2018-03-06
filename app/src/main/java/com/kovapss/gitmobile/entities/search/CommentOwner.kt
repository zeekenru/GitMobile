package com.kovapss.gitmobile.entities.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommentOwner(@SerializedName("avatar_url") val avatarUrl : String,
                        @SerializedName("login") val login : String?) : Parcelable