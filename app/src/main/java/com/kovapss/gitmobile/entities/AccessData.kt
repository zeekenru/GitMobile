package com.kovapss.gitmobile.entities

import com.google.gson.annotations.SerializedName


data class AccessData(@SerializedName("access_token") val token: String,
                      @SerializedName("scope") val scopes : String)
