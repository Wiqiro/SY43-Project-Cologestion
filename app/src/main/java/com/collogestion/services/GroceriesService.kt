package com.collogestion.services

import com.collogestion.data.GroceryList

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

}