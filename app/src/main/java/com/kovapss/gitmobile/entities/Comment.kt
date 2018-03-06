package com.kovapss.gitmobile.entities

import com.google.gson.annotations.SerializedName
import com.kovapss.gitmobile.entities.search.CommentOwner


data class Comment(@SerializedName("id") val id : String,
                   @SerializedName("created_at" ) val createDate : String,
                   @SerializedName("user") val commentOwner: CommentOwner,
                   @SerializedName("body") val commentBody : String)
