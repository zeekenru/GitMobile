package com.kovapss.gitmobile.entities.repository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Readme(@SerializedName("download_url") val htmlUrl : String,
                  @SerializedName("name") val fileName : String) : Parcelable