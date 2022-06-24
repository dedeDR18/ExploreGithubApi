package com.example.exploregithubapi.data.remote.network

import com.example.exploregithubapi.data.remote.response.GithubDto
import com.example.exploregithubapi.data.remote.response.UserDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("/search/users?")
    suspend fun getUsers(
        @Query("q", encoded = true) query: String,
    ): GithubDto

    @GET("/users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): UserDetailDto

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}