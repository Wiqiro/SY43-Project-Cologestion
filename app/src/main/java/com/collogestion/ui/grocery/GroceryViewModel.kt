package com.collogestion.ui.grocery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collogestion.data.GroceryList
import com.collogestion.services.GroceriesService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GroceryUiState(
    val groceryLists: List<GroceryList> = emptyList(),
    val selectedGroceryListId: Int? = null,
    val selectedGroceryList: GroceryList? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class GroceryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GroceryUiState())
    val uiState: StateFlow<GroceryUiState> = _uiState.asStateFlow()

    private fun setLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loading)
    }

    private fun setError(message: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = message)
    }

    private fun setGroceryLists(groceryLists: List<GroceryList>) {
        _uiState.value = _uiState.value.copy(groceryLists = groceryLists)
    }

    private fun setSelectedGroceryList(groceryList: GroceryList?) {
        _uiState.value = _uiState.value.copy(selectedGroceryList = groceryList)
    }

    fun loadUserGroceryLists(userId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { GroceriesService.getUserGroceryLists(userId) }
            result.onSuccess { groceryLists ->
                setGroceryLists(groceryLists)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to load user grocery lists: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun loadHouseShareGroceryLists(houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { GroceriesService.getHouseShareGroceryLists(houseShareId) }
            result.onSuccess { groceryLists ->
                setGroceryLists(groceryLists)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to load house share grocery lists: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun addGroceryList(name: String, assigneeId: Int, houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result =
                runCatching { GroceriesService.addGroceryList(name, assigneeId, houseShareId) }
            result.onSuccess { newGroceryList ->
                setGroceryLists(_uiState.value.groceryLists + newGroceryList)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to add grocery list: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun editGroceryList(groceryListId: Int, name: String, assigneeId: Int, houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching {
                GroceriesService.editGroceryList(
                    groceryListId,
                    name,
                    assigneeId,
                    houseShareId
                )
            }
            result.onSuccess { updatedGroceryList ->
                val updatedList = _uiState.value.groceryLists.map { groceryList ->
                    if (groceryList.id == groceryListId) updatedGroceryList else groceryList
                }
                setGroceryLists(updatedList)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to edit grocery list: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun deleteGroceryList(groceryListId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { GroceriesService.deleteGroceryList(groceryListId) }
            result.onSuccess {
                setGroceryLists(_uiState.value.groceryLists.filter { it.id != groceryListId })
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to delete grocery list: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun selectGroceryList(groceryListId: Int) {
        val selectedGroceryList = _uiState.value.groceryLists.find { it.id == groceryListId }
        _uiState.value = _uiState.value.copy(
            selectedGroceryListId = groceryListId,
            selectedGroceryList = selectedGroceryList
        )
    }

    fun clearError() {
        setError(null)
    }
}
