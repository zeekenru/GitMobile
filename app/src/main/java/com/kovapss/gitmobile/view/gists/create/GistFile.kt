package com.kovapss.gitmobile.view.gists.create

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GistFile(val filename : String, val content : String) : Parcelable

