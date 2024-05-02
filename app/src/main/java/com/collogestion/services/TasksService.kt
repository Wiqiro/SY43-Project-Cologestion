package com.collogestion.services

import com.collogestion.data.Task

object TasksService {
    fun getUserTasks(userId: Int, completion: (List<Task>) -> Unit) {
        HttpClient.getRequest("/tasks/user/$userId") { response ->
            val tasks = HttpClient.gson.fromJson(response, Array<Task>::class.java).toList()
            completion(tasks)
        }
    }

    fun getHouseShareTasks(houseShareId: Int, completion: (List<Task>) -> Unit) {
        HttpClient.getRequest("/tasks/house_share/$houseShareId") { response ->
            val tasks = HttpClient.gson.fromJson(response, Array<Task>::class.java).toList()
            completion(tasks)
        }
    }
}