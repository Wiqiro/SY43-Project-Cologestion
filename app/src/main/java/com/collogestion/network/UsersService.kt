package com.collogestion.network

import com.collogestion.data.User

object UsersService {
    suspend fun getUser(id: Int? = null): User {
        val response = HttpClient.getRequest(if (id != null) "/users/$id" else "/users/me")
        return HttpClient.gson.fromJson(response, User::class.java)
    }

    suspend fun getHouseShareUsers(houseShareId: Int): List<User> {
        val response = HttpClient.getRequest("/house_shares/user/$houseShareId")
        return HttpClient.gson.fromJson(response, Array<User>::class.java).toList()
    }

    suspend fun addUser(
        firstname: String, lastname: String, email: String, phone: String, password: String
    ): User {
        val body = mapOf(
            "first_name" to firstname,
            "last_name" to lastname,
            "email" to email,
            "phone" to phone,
            "password" to password
        )

        val response = HttpClient.postRequest("/users", body)
        return HttpClient.gson.fromJson(response, User::class.java)
    }

    suspend fun changePassword(newPassword: String, oldPassword: String): Boolean {
        try {
            val body = mapOf("new_password" to newPassword, "old_password" to oldPassword)
            val response = HttpClient.putRequest("/users/me/password", body)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
