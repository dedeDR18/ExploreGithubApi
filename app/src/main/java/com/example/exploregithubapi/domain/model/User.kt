package com.example.exploregithubapi.domain.model

data class User(
    val avatar_url: String,
    val id: Int,
    val login: String,
    val email: String,
    val location: String,
    val bio: String,
    val name: String
)