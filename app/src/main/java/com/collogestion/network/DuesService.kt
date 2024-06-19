package com.collogestion.network

import android.util.Log
import com.collogestion.data.Due

object DuesService {
    suspend fun getHouseShareDues(houseShareId: Int): List<Due> {
        val response = HttpClient.getRequest("/dues/house_share/$houseShareId")
        return HttpClient.gson.fromJson(response, Array<Due>::class.java).toList()
    }

    suspend fun getUserDues(userId: Int): List<Due> {
        val response = HttpClient.getRequest("/dues/user/$userId")
        return HttpClient.gson.fromJson(response, Array<Due>::class.java).toList()
    }

    suspend fun addDue(title: String, amount: Double, creditorId: Int, debtorId: Int, houseShareId: Int): Due {
        val data = mapOf(
            "amount" to amount,
            "title" to title,
            "creditor_id" to creditorId,
            "debtor_id" to debtorId,
            "house_share_id" to houseShareId
        )

        val response = HttpClient.postRequest("/dues", data)
        return HttpClient.gson.fromJson(response, Due::class.java)
    }

    suspend fun editDue(
        dueId: Int,
        amount: Double,
        creditorId: Int,
        debtorId: Int,
        houseShareId: Int
    ): Due {
        val data = mapOf(
            "amount" to amount,
            "creditor_id" to creditorId,
            "debtor_id" to debtorId,
            "house_share_id" to houseShareId
        )

        val response = HttpClient.putRequest("/dues/$dueId", data)
        return HttpClient.gson.fromJson(response, Due::class.java)
    }

    suspend fun deleteDue(dueId: Int): Due {
        val response = HttpClient.deleteRequest("/dues/$dueId")
        return HttpClient.gson.fromJson(response, Due::class.java)
    }
}
