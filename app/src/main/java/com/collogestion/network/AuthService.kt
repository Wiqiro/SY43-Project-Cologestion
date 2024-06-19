package com.collogestion.network

import android.content.Context
import android.content.SharedPreferences
import com.collogestion.data.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import okhttp3.FormBody

object AuthService {
    private lateinit var sharedPreferences: SharedPreferences
    private var _loggedIn = MutableStateFlow(false)
    private var _apiReachable = MutableStateFlow(false)


    suspend fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        if (HttpClient.isApiReachable()) {
            val token = getToken()
            _apiReachable.value = true
            if (token != null) {
                HttpClient.setToken(token)
                _loggedIn.value = true
            }
        } else {
            _apiReachable.value = false
        }
    }

    val loggedIn: Flow<Boolean> = _loggedIn

    val isLoggedIn: Boolean
        get() = _loggedIn.value

    val apiReachable: Flow<Boolean> = _apiReachable

    suspend fun login(email: String, password: String) {
        return withContext(Dispatchers.IO) {
            try {
                val response = HttpClient.postRequest(
                    "/auth/token", FormBody.Builder()
                        .add("username", email)
                        .add("password", password)
                        .build()
                )
                val token = HttpClient.gson.fromJson(response, Token::class.java)
                HttpClient.setToken(token.access_token)
                saveToken(token.access_token)
                _loggedIn.value = true
            } catch (e: Exception) {
                _loggedIn.value = false
            }
        }
    }

    fun logout() {
        sharedPreferences.edit().remove("token").apply()
        HttpClient.removeToken()
        _loggedIn.value = false
    }

    private fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    private fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }
}
