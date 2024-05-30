package com.collogestion.network

import android.content.Context
import android.content.SharedPreferences
import com.collogestion.data.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody

object AuthService {
    private lateinit var sharedPreferences: SharedPreferences
    private var loggedIn = false

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val token = getToken()
        if (token != null) {
            HttpClient.setToken(token)
            loggedIn = true
        }
    }

    fun isLoggedIn(): Boolean {
        return loggedIn
    }

    suspend fun login(email: String, password: String): String {
        return withContext(Dispatchers.IO) {
            val response = HttpClient.postRequest(
                "/auth/token", FormBody.Builder()
                    .add("username", email)
                    .add("password", password)
                    .build()
            )
            response.let {
                val token = HttpClient.gson.fromJson(it, Token::class.java)
                HttpClient.setToken(token.access_token)
                saveToken(token.access_token)
                loggedIn = true
            }
            response
        }
    }

    fun logout() {
        sharedPreferences.edit().remove("token").apply()
        HttpClient.removeToken()
        loggedIn = false
    }

    private fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    private fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }
}
