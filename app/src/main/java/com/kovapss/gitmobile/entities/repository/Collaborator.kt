package com.kovapss.gitmobile.entities.repository

import com.google.gson.annotations.SerializedName


data class Collaborator(@SerializedName("avatar_url") val avatarUrl: String,
                        @SerializedName("id") val id: String,
                        @SerializedName("login") val login : String)