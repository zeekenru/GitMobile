package com.kovapss.gitmobile.entities.commit

import com.google.gson.annotations.SerializedName
import java.util.*


data class CommitAuthor(@SerializedName("name") val name: String,
                        @SerializedName("date") val date : String)