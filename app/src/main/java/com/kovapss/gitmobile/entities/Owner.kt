package com.kovapss.gitmobile.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
        @SerializedName("login")
        val login: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("avatar_url")
        val avatarUrl: String?,
        @SerializedName("id")
        val id: Int) : Parcelable