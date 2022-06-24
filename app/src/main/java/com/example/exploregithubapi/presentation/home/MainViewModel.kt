package com.example.exploregithubapi.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exploregithubapi.domain.model.User
import com.example.exploregithubapi.domain.usecase.GetUsers
import com.example.exploregithubapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsers: GetUsers
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    var searchQuery: StateFlow<String> = _searchQuery

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    var uiState:StateFlow<UiState> = _uiState

    private var searchJob: Job? = null

    fun onSearch(query:String){
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getUsers(query)
                .onEach { result ->
                    when (result){
                        is Resource.Success -> {
                            val data = result.data ?: emptyList()
                            val message = result.message ?: "0"
                            _uiState.value = UiState.Success(data, message)
                        }
                        is Resource.Error -> {
                            val error = result.message ?: "unknown error !"
                            _uiState.value = UiState.Error(error)
                        }
                        is Resource.Loading -> {
                            _uiState.value = UiState.Loading
                        }
                    }

                }.launchIn(this)
        }
    }


    sealed class UiState{
        data class Success(val users: List<User>, val message: String): UiState()
        data class Error(val message: String): UiState()
        object Loading: UiState()
        object Empty: UiState()
    }
}