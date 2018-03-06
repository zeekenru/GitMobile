package com.kovapss.gitmobile.entities.search

import com.google.gson.annotations.SerializedName
import com.kovapss.gitmobile.entities.issue.Issue


data class IssueSearchResponse(@SerializedName("items") val results : List<Issue>)