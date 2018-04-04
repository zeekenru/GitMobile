package com.kovapss.gitmobile.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kovapss.gitmobile.entities.search.GistCreateFile
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateGistData(@SerializedName("description") val description : String = "",
                          @SerializedName("files") val files: LinkedHashMap<String, GistCreateFile>,
                          @SerializedName("public") val public : Boolean) : Parcelable