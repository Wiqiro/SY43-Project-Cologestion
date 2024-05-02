package com.collogestion.services

import com.collogestion.data.GroceryList
import okhttp3.FormBody

object GroceriesService {
    fun getUserGroceryLists(userId: Int, completion: (List<GroceryList>) -> Unit) {
        HttpClient.getRequest("/groceries/user/$userId") { response ->
            val groceryLists = HttpClient.gson.fromJson(response, Array<GroceryList>::class.java).toList()
            completion(groceryLists)
        }
    }

    fun getHouseShareGroceryLists(houseShareId: Int, completion: (List<GroceryList>) -> Unit) {
        HttpClient.getRequest("/groceries/house_share/$houseShareId") { response ->
            val groceryLists = HttpClient.gson.fromJson(response, Array<GroceryList>::class.java).toList()
            completion(groceryLists)
        }
    }

    fun addGroceryList(
        name: String, assigneeId: Int, houseShareId: Int, completion: (GroceryList) -> Unit
    ) {
        val body = mapOf(
            "name" to name,
            "assignee_id" to assigneeId,
            "house_share_id" to houseShareId
        )

        HttpClient.postRequest("/groceries", body) { response ->
            val groceryList = HttpClient.gson.fromJson(response, GroceryList::class.java)
            completion(groceryList)
        }
    }

    fun editGroceryList(groceryListId: Int, name: String, assigneeId: Int, houseShareId: Int, completion: (GroceryList) -> Unit) {
        val body = mapOf(
            "name" to name,
            "assignee_id" to assigneeId,
            "house_share_id" to houseShareId
        )

        HttpClient.postRequest("/groceries/$groceryListId", body) { response ->
            val groceryList = HttpClient.gson.fromJson(response, GroceryList::class.java)
            completion(groceryList)
        }
    }

    fun deleteGroceryList(groceryListId: Int, completion: (GroceryList) -> Unit) {
        HttpClient.deleteRequest("/groceries/$groceryListId") { response ->
            val groceryList = HttpClient.gson.fromJson(response, GroceryList::class.java)
            completion(groceryList)
        }
    }

}