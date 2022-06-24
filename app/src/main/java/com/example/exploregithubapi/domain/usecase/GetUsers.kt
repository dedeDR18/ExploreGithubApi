package com.example.exploregithubapi.domain.usecase

import com.example.exploregithubapi.domain.model.User
import com.example.exploregithubapi.domain.repository.UserRepository
import com.example.exploregithubapi.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUsers(private val repository: UserRepository){
    operator fun invoke(name: String) : Flow<Resource<List<User>>> {
        if (name.isBlank()){
            return flow {  }
        } else {
            return repository.getUsers(name)
        }
    }

}