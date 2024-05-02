package com.collogestion.services

import com.collogestion.data.Due

object DuesService {
    fun getHouseShareDues(houseShareId: Int, completion: (List<Due>) -> Unit) {
        HttpClient.getRequest("/dues/house_share/$houseShareId") { response ->
            val dues = HttpClient.gson.fromJson(response, Array<Due>::class.java).toList()
            completion(dues)
        }
    }

    fun getUserDues(userId: Int, completion: (List<Due>) -> Unit) {
        HttpClient.getRequest("/dues/user/$userId") { response ->
            val dues = HttpClient.gson.fromJson(response, Array<Due>::class.java).toList()
            completion(dues)
        }
    }
}