package com.example.exploregithubapi.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exploregithubapi.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class,],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao

    companion object{
        const val DATABASE_NAME = "githubuser_db"
    }

}