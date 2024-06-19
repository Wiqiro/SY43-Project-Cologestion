package com.collogestion.ui.user

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collogestion.data.User
import com.collogestion.network.AuthService
import com.collogestion.network.UsersService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserUiState(
    val user: User? = null,
    val houseShareUsers: List<User> = emptyList(),
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class UserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    private fun setLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loading)
    }

    private fun setError(message: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = message)
    }

    private fun setUser(user: User?) {
        _uiState.value = _uiState.value.copy(user = user)
    }

    private fun setHouseShareUsers(users: List<User>) {
        _uiState.value = _uiState.value.copy(houseShareUsers = users)
    }

    private fun setLoggedIn(loggedIn: Boolean) {
        _uiState.value = _uiState.value.copy(isLoggedIn = loggedIn)
    }

    fun login(email: String, password: String) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { AuthService.login(email, password) }
            result.onSuccess {
                setLoggedIn(true)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to login: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun logout() {
        AuthService.logout()
        setLoggedIn(false)
    }

    fun loadUser() {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { UsersService.getUser() }
            result.onSuccess { user ->
                setUser(user)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to load user: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun loadHouseShareUsers(houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { UsersService.getHouseShareUsers(houseShareId) }
            result.onSuccess { users ->
                setHouseShareUsers(users)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to load house share users: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun addUser(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String
    ) {
        setLoading(true)
        viewModelScope.launch {
            val result =
                runCatching { UsersService.addUser(firstName, lastName, email, phone, password) }
            result.onSuccess { newUser ->
                setUser(newUser)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to add user: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun clearError() {
        setError(null)
    }
}
