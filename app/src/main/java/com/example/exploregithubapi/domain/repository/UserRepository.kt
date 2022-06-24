package com.example.exploregithubapi.domain.repository

import com.example.exploregithubapi.domain.model.User
import com.example.exploregithubapi.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(name: String): Flow<Resource<List<User>>>
}