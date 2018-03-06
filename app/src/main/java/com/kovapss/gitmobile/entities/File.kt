package com.kovapss.gitmobile.entities

import com.google.gson.annotations.SerializedName


data class File(@SerializedName("filename") val filename : String,
                @SerializedName("size") val size : String,
                @SerializedName("content") val content : String)

