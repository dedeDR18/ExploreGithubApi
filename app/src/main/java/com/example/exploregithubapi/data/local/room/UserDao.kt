package com.example.exploregithubapi.data.local.room

import androidx.room.*
import com.example.exploregithubapi.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(listUser: List<UserEntity>)

    @Query("SELECT * FROM userentity WHERE login LIKE '%' || :name || '%'")
    suspend fun getUsers(name: String): List<UserEntity>

    @Query("DELETE FROM userentity WHERE login IN(:listName)")
    suspend fun deleteUsers(listName: List<String>)

    @Query("SELECT * FROM userentity WHERE login LIKE :username")
    suspend fun getUser(username: String): List<UserEntity>

    @Query("UPDATE userentity SET name = :name, email = :email, bio = :bio, location = :location WHERE login LIKE :username")
    suspend fun updateUser(
        username: String,
        name: String,
        email: String,
        bio: String,
        location: String
    )
}