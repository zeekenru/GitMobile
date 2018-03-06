package com.kovapss.gitmobile.entities.commit

import com.google.gson.annotations.SerializedName


data class CommitResponse(@SerializedName("commit") val commit: Commit)