package com.kovapss.gitmobile.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user")
data class UserDb(
        @field:PrimaryKey(autoGenerate = true) var id: Long = 0,
        @ColumnInfo(name = "github_id") var githubId: Long = 0,
        @ColumnInfo(name = "avatar_url") var avatarUrl: String = "empty",
        @ColumnInfo(name = "name") var name: String = "empty",
        @ColumnInfo(name = "location") var location: String? = "empty",
        @ColumnInfo(name = "email") var email: String? = "empty",
        @ColumnInfo(name = "bio") var bio: String? = "empty",
        @ColumnInfo(name = "followers") var followers: String = "empty",
        @ColumnInfo(name = "following") var following: String = "empty")



