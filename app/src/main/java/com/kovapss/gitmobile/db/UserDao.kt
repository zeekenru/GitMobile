package com.kovapss.gitmobile.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.kovapss.gitmobile.entities.UserDb
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): Single<UserDb>

    @Insert(onConflict = REPLACE)
    fun insertUser(user: UserDb)

    @Update(onConflict = REPLACE)
    fun updateUser(user: UserDb)

    @Delete
    fun deleteUser(user: UserDb)

}