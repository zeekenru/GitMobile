package com.kovapss.gitmobile.entities.search

import com.google.gson.annotations.SerializedName
import com.kovapss.gitmobile.entities.commit.Commit


data class CommitSearchResponse(@SerializedName("items") val results : List<Commit>)