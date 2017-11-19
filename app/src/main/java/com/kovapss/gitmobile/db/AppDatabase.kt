package com.kovapss.gitmobile.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.kovapss.gitmobile.entities.UserDb

@Database(entities = arrayOf(UserDb::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}