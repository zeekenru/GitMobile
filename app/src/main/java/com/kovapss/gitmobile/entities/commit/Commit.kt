package com.kovapss.gitmobile.entities.commit

import com.google.gson.annotations.SerializedName
import com.kovapss.gitmobile.entities.DelegatesAdapterModel


data class Commit(@SerializedName("author") val author : CommitAuthor,
                  @SerializedName("message") val message : String?,
                  @SerializedName("comment_count") val commentCount : Int,
                  @SerializedName("verification") val verification: CommitVerification) : DelegatesAdapterModel