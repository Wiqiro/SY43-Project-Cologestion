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
}