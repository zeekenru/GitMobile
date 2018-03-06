package com.kovapss.gitmobile.entities.search

import com.google.gson.annotations.SerializedName
import com.kovapss.gitmobile.entities.repository.Repository


data class RepositorySearchResponse(@SerializedName("items") val results: List<Repository>)