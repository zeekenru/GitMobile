package com.kovapss.gitmobile.entities

import com.google.gson.annotations.SerializedName
import com.kovapss.gitmobile.entities.repository.Repository


data class Notification(@SerializedName("repository") val repository : Repository,
                        @SerializedName("reason") val reason : String)