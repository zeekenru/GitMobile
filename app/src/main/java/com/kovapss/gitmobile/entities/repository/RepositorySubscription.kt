package com.kovapss.gitmobile.entities.repository

import com.google.gson.annotations.SerializedName


data class RepositorySubscription(@SerializedName("subscribed") val subscribed : Boolean)