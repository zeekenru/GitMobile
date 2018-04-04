package com.kovapss.gitmobile.entities.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateRepositoryFileData(val path: String,
                                    val commitMessage: String,
                                    val content: String,
                                    val sha: String,
                                    val branch: String) : Parcelable