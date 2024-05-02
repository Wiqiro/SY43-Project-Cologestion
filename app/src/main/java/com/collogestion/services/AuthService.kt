package com.collogestion.services

import android.content.Context
import android.content.SharedPreferences
import com.collogestion.data.Token
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

    fun login(email: String, password: String, callback: (String?) -> Unit) {

        HttpClient.postRequest(
            "/auth/token", FormBody.Builder()
                .add("username", email)
                .add("password", password)
                .build()
        ) { response ->
            response?.let {
                val token = HttpClient.gson.fromJson(it, Token::class.java)
                HttpClient.setToken(token.access_token)
                saveToken(token.access_token)
            }
            callback(response)
        }
    }

    fun logout() {
        sharedPreferences.edit().remove("token").apply()
        HttpClient.removeToken()
    }

    private fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    private fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }
}