package com.collogestion.network

import com.collogestion.data.HouseShare

object HouseSharesService {
    suspend fun getHouseShare(id: Int): HouseShare {
        val response = HttpClient.getRequest("/house_shares/$id")
        return HttpClient.gson.fromJson(response, HouseShare::class.java)
    }

    suspend fun getUsersHouseShares(userId: Int): List<HouseShare> {
        val response = HttpClient.getRequest("/house_shares/user/$userId")
        return HttpClient.gson.fromJson(response, Array<HouseShare>::class.java).toList()
    }

    suspend fun addHouseShare(name: String): HouseShare {
        val body = mapOf(
            "name" to name
        )

        val response = HttpClient.postRequest("/house_shares", body)
        return HttpClient.gson.fromJson(response, HouseShare::class.java)
    }

    suspend fun addUserToHouseShare(email: String, houseShareId: Int): HouseShare {
        val body = mapOf(
            "email" to email
        )

        val response = HttpClient.postRequest("/house_shares/$houseShareId/members", body)
        return HttpClient.gson.fromJson(response, HouseShare::class.java)
    }

    suspend fun editHouseShare(houseShareId: Int, name: String, imageUrl: String): HouseShare {
        val body = mapOf(
            "name" to name, "image" to imageUrl
        )

        val response = HttpClient.putRequest("/house_shares/$houseShareId", body)
        return HttpClient.gson.fromJson(response, HouseShare::class.java)
    }

    suspend fun deleteHouseShare(houseShareId: Int): HouseShare {
        val response = HttpClient.deleteRequest("/house_shares/$houseShareId")
        return HttpClient.gson.fromJson(response, HouseShare::class.java)
    }

    suspend fun removeUserFromHouseShare(userId: Int, houseShareId: Int): HouseShare {
        val response = HttpClient.deleteRequest("/house_shares/$houseShareId/members/$userId")
        return HttpClient.gson.fromJson(response, HouseShare::class.java)
    }
}
