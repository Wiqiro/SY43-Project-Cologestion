package com.collogestion.services

import com.collogestion.data.Due

import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

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

    fun addDue(
        amount: Double, creditorId: Int, debtorId: Int, houseShareId: Int, completion: (Due) -> Unit
    ) {
        val data = mapOf(
            "amount" to amount,
            "creditor_id" to creditorId,
            "debtor_id" to debtorId,
            "house_share_id" to houseShareId
        )

        HttpClient.postRequest("/dues", data) { response ->
            val due = HttpClient.gson.fromJson(response, Due::class.java)
            completion(due)
        }
    }

    fun editDue(
        dueId: Int,
        amount: Double,
        creditorId: Int,
        debtorId: Int,
        houseShareId: Int,
        completion: (Due) -> Unit
    ) {
        val data = mapOf(
            "amount" to amount,
            "creditor_id" to creditorId,
            "debtor_id" to debtorId,
            "house_share_id" to houseShareId
        )

        HttpClient.putRequest("/dues/$dueId", data) { response ->
            val due = HttpClient.gson.fromJson(response, Due::class.java)
            completion(due)
        }
    }

    fun deleteDue(dueId: Int, completion: (Due) -> Unit) {
        HttpClient.deleteRequest("/dues/$dueId") { response ->
            val due = HttpClient.gson.fromJson(response, Due::class.java)
            completion(due)
        }
    }
}