package com.kovapss.gitmobile.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Parcelize
data class GistFile(@SerializedName("size") val  size : Int,
                    @SerializedName("raw_url") val rawUrl : String,
                    @SerializedName("content") val content : String?,
                    @SerializedName("language") val language : String?) : Parcelable