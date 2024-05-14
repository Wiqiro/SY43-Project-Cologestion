package com.collogestion.services

import com.collogestion.data.HouseShare

object HouseSharesService {
    fun getHouseShare(id: Int, completion: (HouseShare) -> Unit) {
        HttpClient.getRequest("/house_shares/$id") { response ->
            val houseShare = HttpClient.gson.fromJson(response, HouseShare::class.java)
            completion(houseShare)
        }
    }

    fun getUsersHouseShares(userId: Int, completion: (List<HouseShare>) -> Unit) {
        HttpClient.getRequest("/house_shares/user/$userId") { response ->
            val houseShares = HttpClient.gson.fromJson(response, Array<HouseShare>::class.java).toList()
            completion(houseShares)
        }
    }

    fun addHouseShare(name: String, imageUrl: String, completion: (HouseShare) -> Unit) {
        val body = mapOf(
            "name" to name,
            "image" to imageUrl
        )

        HttpClient.postRequest("/house_shares", body) { response ->
            val houseShare = HttpClient.gson.fromJson(response, HouseShare::class.java)
            completion(houseShare)
        }
    }

    fun editHouseShare(houseShareId: Int, name: String, imageUrl: String, completion: (HouseShare) -> Unit) {
        val body = mapOf(
            "name" to name,
            "image" to imageUrl
        )

        HttpClient.putRequest("/house_shares/$houseShareId", body) { response ->
            val houseShare = HttpClient.gson.fromJson(response, HouseShare::class.java)
            completion(houseShare)
        }
    }

    fun deleteHouseShare(houseShareId: Int, completion: (HouseShare) -> Unit) {
        HttpClient.deleteRequest("/house_shares/$houseShareId") { response ->
            val houseShare = HttpClient.gson.fromJson(response, HouseShare::class.java)
            completion(houseShare)
        }
    }
}