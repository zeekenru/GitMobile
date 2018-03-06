package com.kovapss.gitmobile.entities.repository

import com.google.gson.annotations.SerializedName


data class Topics(@SerializedName("names") val topics : List<String>)