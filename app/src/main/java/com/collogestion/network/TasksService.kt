package com.collogestion.network

import com.collogestion.data.Task

object TasksService {
    suspend fun getUserTasks(userId: Int): List<Task> {
        val response = HttpClient.getRequest("/tasks/user/$userId")
        return HttpClient.gson.fromJson(response, Array<Task>::class.java).toList()
    }

    suspend fun getHouseShareTasks(houseShareId: Int): List<Task> {
        val response = HttpClient.getRequest("/tasks/house_share/$houseShareId")
        return HttpClient.gson.fromJson(response, Array<Task>::class.java).toList()
    }

    suspend fun addTask(
        name: String, deadline: String, assigneeId: Int, houseShareId: Int
    ): Task {
        val data = mapOf(
            "name" to name,
            "deadline" to deadline,
            "assignee_id" to assigneeId,
            "house_share_id" to houseShareId
        )

        val response = HttpClient.postRequest("/tasks", data)
        return HttpClient.gson.fromJson(response, Task::class.java)
    }

    suspend fun editTask(
        taskId: Int,
        name: String,
        deadline: String,
        assigneeId: Int,
        houseShareId: Int
    ): Task {
        val data = mapOf(
            "name" to name,
            "deadline" to deadline,
            "assignee_id" to assigneeId,
            "house_share_id" to houseShareId
        )

        val response = HttpClient.putRequest("/tasks/$taskId", data)
        return HttpClient.gson.fromJson(response, Task::class.java)
    }

    suspend fun deleteTask(taskId: Int): Task {
        val response = HttpClient.deleteRequest("/tasks/$taskId")
        return HttpClient.gson.fromJson(response, Task::class.java)
    }
}
