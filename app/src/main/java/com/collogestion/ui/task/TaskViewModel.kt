package com.collogestion.ui.task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collogestion.data.Task
import com.collogestion.network.TasksService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val selectedTaskId: Int? = null,
    val selectedTask: Task? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class TaskViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    private fun setLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loading)
    }

    private fun setError(message: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = message)
    }

    private fun setTasks(tasks: List<Task>) {
        _uiState.value = _uiState.value.copy(tasks = tasks)
    }

    private fun setSelectedTask(task: Task?) {
        _uiState.value = _uiState.value.copy(selectedTask = task)
    }

    fun loadUserTasks(userId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { TasksService.getUserTasks(userId) }
            result.onSuccess { tasks ->
                setTasks(tasks)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to load user tasks: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun loadHouseShareTasks(houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { TasksService.getHouseShareTasks(houseShareId) }
            result.onSuccess { tasks ->
                setTasks(tasks)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to load house share tasks: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun addTask(name: String, deadline: String, assigneeId: Int, houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { TasksService.addTask(name, deadline, assigneeId, houseShareId) }
            result.onSuccess { newTask ->
                setTasks(_uiState.value.tasks + newTask)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to add task: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun editTask(taskId: Int, name: String, deadline: String, assigneeId: Int, houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { TasksService.editTask(taskId, name, deadline, assigneeId, houseShareId) }
            result.onSuccess { updatedTask ->
                val updatedList = _uiState.value.tasks.map { task ->
                    if (task.id == taskId) updatedTask else task
                }
                setTasks(updatedList)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to edit task: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun deleteTask(taskId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { TasksService.deleteTask(taskId) }
            result.onSuccess {
                setTasks(_uiState.value.tasks.filter { it.id != taskId })
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to delete task: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun selectTask(taskId: Int) {
        val selectedTask = _uiState.value.tasks.find { it.id == taskId }
        _uiState.value = _uiState.value.copy(
            selectedTaskId = taskId,
            selectedTask = selectedTask
        )
    }

    fun clearError() {
        setError(null)
    }
}
