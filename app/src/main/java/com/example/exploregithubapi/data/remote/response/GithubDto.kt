package com.example.exploregithubapi.data.remote.response

import com.example.exploregithubapi.data.local.entity.UserEntity

data class GithubDto(
    val incomplete_results: Boolean,
    val items: List<UserDto>,
    val total_count: Int
)