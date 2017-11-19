package com.kovapss.gitmobile.di.providers

import android.arch.persistence.room.Room
import android.content.Context
import com.kovapss.gitmobile.db.AppDatabase
import javax.inject.Provider


class DatabaseProvider(private val context : Context) : Provider<AppDatabase> {

    companion object {
        const val dbName = "gitmobile_db"
    }

    override fun get(): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()

}