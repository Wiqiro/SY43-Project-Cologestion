package com.collogestion.network

import com.collogestion.data.User

object UsersService {
    suspend fun getUser(id: Int): User {
        val response = HttpClient.getRequest("/users/$id")
        return HttpClient.gson.fromJson(response, User::class.java)
    }

    suspend fun getHouseShareUsers(houseShareId: Int): List<User> {
        val response = HttpClient.getRequest("/house_shares/$houseShareId/users")
        return HttpClient.gson.fromJson(response, Array<User>::class.java).toList()
    }

    suspend fun addUser(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
        password: String
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
}
