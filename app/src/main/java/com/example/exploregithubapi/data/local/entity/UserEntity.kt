package com.example.exploregithubapi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exploregithubapi.domain.model.User

@Entity
data class UserEntity(
    val avatar_url: String,
    @PrimaryKey
    val id: Int,
    val login: String,
    val location: String,
    val bio: String,
    val email: String,
    val name: String
) {
    fun toUser(): User {
        return User(
            id = id,
            login = login,
            location = location,
            bio = bio,
            avatar_url = avatar_url,
            email = email,
            name = name
        )
    }
}