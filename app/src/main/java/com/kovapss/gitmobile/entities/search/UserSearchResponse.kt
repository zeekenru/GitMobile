package com.kovapss.gitmobile.entities.search

import com.google.gson.annotations.SerializedName
import com.kovapss.gitmobile.entities.User


data class UserSearchResponse(@SerializedName("items") val results : List<User>)