package com.kovapss.gitmobile.entities.search

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GistCreateFile(@SerializedName("content") val content : String) : Parcelable