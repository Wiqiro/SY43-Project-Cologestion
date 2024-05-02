package com.collogestion.services

import com.collogestion.data.GroceryList
import okhttp3.FormBody

object GroceriesService {
    fun getUserGroceryLists(userId: Int, completion: (List<GroceryList>) -> Unit) {
        HttpClient.getRequest("/grocery_lists/user/$userId") { response ->
            val groceryLists = HttpClient.gson.fromJson(response, Array<GroceryList>::class.java).toList()
            completion(groceryLists)
        }
    }

    fun getHouseShareGroceryLists(houseShareId: Int, completion: (List<GroceryList>) -> Unit) {
        HttpClient.getRequest("/grocery_lists/house_share/$houseShareId") { response ->
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

        HttpClient.postRequest("/grocery_lists", body) { response ->
            val groceryList = HttpClient.gson.fromJson(response, GroceryList::class.java)
            completion(groceryList)
        }
    }

}