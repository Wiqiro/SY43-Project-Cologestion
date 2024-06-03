package com.collogestion.services

import com.collogestion.data.GroceryList
import com.collogestion.network.HttpClient

object GroceriesService {
    suspend fun getUserGroceryLists(userId: Int): List<GroceryList> {
        val response = HttpClient.getRequest("/groceries/user/$userId")
        return HttpClient.gson.fromJson(response, Array<GroceryList>::class.java).toList()
    }

    suspend fun getHouseShareGroceryLists(houseShareId: Int): List<GroceryList> {
        val response = HttpClient.getRequest("/groceries/house_share/$houseShareId")
        return HttpClient.gson.fromJson(response, Array<GroceryList>::class.java).toList()
    }

    suspend fun addGroceryList(name: String, assigneeId: Int, houseShareId: Int): GroceryList {
        val body = mapOf(
            "name" to name,
            "assignee_id" to assigneeId,
            "house_share_id" to houseShareId
        )

        val response = HttpClient.postRequest("/groceries", body)
        return HttpClient.gson.fromJson(response, GroceryList::class.java)
    }

    suspend fun editGroceryList(
        groceryListId: Int,
        name: String,
        assigneeId: Int,
        houseShareId: Int
    ): GroceryList {
        val body = mapOf(
            "name" to name,
            "assignee_id" to assigneeId,
            "house_share_id" to houseShareId
        )

        val response = HttpClient.postRequest("/groceries/$groceryListId", body)
        return HttpClient.gson.fromJson(response, GroceryList::class.java)
    }

    suspend fun deleteGroceryList(groceryListId: Int): GroceryList {
        val response = HttpClient.deleteRequest("/groceries/$groceryListId")
        return HttpClient.gson.fromJson(response, GroceryList::class.java)
    }
}
