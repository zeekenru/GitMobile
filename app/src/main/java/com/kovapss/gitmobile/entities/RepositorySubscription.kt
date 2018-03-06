package com.kovapss.gitmobile.entities

import com.google.gson.annotations.SerializedName


data class RepositorySubscription(@SerializedName("subscribed") val subscribed : Boolean)