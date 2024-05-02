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

    fun addTask(
        name: String, deadline: String, assigneeId: Int, houseShareId: Int, completion: (Task) -> Unit
    ) {
        val data = mapOf(
            "name" to name,
            "deadline" to deadline,
            "assignee_id" to assigneeId,
            "house_share_id" to houseShareId
        )

        HttpClient.postRequest("/tasks", data) { response ->
            val task = HttpClient.gson.fromJson(response, Task::class.java)
            completion(task)
        }
    }

    fun editTask(
        taskId: Int,
        name: String,
        deadline: String,
        assigneeId: Int,
        houseShareId: Int,
        completion: (Task) -> Unit
    ) {
        val data = mapOf(
            "name" to name,
            "deadline" to deadline,
            "assignee_id" to assigneeId,
            "house_share_id" to houseShareId
        )

        HttpClient.putRequest("/tasks/$taskId", data) { response ->
            val task = HttpClient.gson.fromJson(response, Task::class.java)
            completion(task)
        }
    }

    fun deleteTask(taskId: Int, completion: (Task) -> Unit) {
        HttpClient.deleteRequest("/tasks/$taskId") { response ->
            val task = HttpClient.gson.fromJson(response, Task::class.java)
            completion(task)
        }
    }
}