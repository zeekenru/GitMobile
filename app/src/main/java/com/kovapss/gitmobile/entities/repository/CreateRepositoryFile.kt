package com.kovapss.gitmobile.entities.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateRepositoryFile(val path : String = "",
                                val message : String,
                                val content : String,
                                val branch : String) : Parcelable
