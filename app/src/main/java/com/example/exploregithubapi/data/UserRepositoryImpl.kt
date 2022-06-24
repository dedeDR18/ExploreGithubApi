package com.example.exploregithubapi.data

import android.util.Log
import com.example.exploregithubapi.data.local.room.UserDao
import com.example.exploregithubapi.data.local.room.UserDatabase
import com.example.exploregithubapi.data.remote.network.GithubApiService
import com.example.exploregithubapi.domain.model.User
import com.example.exploregithubapi.domain.repository.UserRepository
import com.example.exploregithubapi.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class UserRepositoryImpl(
    private val api: GithubApiService,
    private val dao: UserDao
) : UserRepository {
    override fun getUsers(name: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        val users = dao.getUsers(name).map { it.toUser() }
        emit(Resource.Loading(data = users))

        try {
            val remoteUsers = api.getUsers(name)

            dao.deleteUsers(remoteUsers.items.map { it.login })
            dao.insertUsers(remoteUsers.items.map { it.toUserEntity() })
        } catch (e: HttpException) {
            emit(Resource.Error("oops, something went wrong !", users))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server!, check your internet connection.", users))
        }

        val newUsers = dao.getUsers(name).map { it.toUser() }
        emit(Resource.Success(data = newUsers, "0"))


        try {
            newUsers.map { user ->
                coroutineScope {
                    val detail = async { api.getDetailUser(user.login) }.await()
                    val email = detail.email ?: "-"
                    val bio = detail.bio ?: "-"
                    val location = detail.location ?: "-"

                    dao.updateUser(detail.login, detail.name, email, bio, location)
                }
                    val updatedUser = dao.getUser(user.login).map { it.toUser() }
                    emit(Resource.Success(data = updatedUser, "1"))
            }
            
        }catch (e: Exception){
                Log.d("MAIN", "error = ${e.message}")
        }


    }
}