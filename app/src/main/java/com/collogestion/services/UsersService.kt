package com.collogestion.services

import com.collogestion.data.User

object UsersService {
    fun getUser(id: Int, completion: (User) -> Unit) {
        HttpClient.getRequest("/users/$id") { response ->
            val user = HttpClient.gson.fromJson(response, User::class.java)
            completion(user)
        }
    }

    fun getHouseShareUsers(houseShareId: Int, completion: (List<User>) -> Unit) {
        HttpClient.getRequest("/house_shares/$houseShareId/users") { response ->
            val users = HttpClient.gson.fromJson(response, Array<User>::class.java).toList()
            completion(users)
        }
    }
}