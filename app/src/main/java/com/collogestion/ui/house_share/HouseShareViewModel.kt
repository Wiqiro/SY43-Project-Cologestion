package com.collogestion.ui.house_share

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collogestion.data.HouseShare
import com.collogestion.network.HouseSharesService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HouseShareUiState(
    val houseShares: List<HouseShare> = emptyList(),
    val selectedHouseShare: HouseShare? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class HouseShareViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HouseShareUiState())
    val uiState: StateFlow<HouseShareUiState> = _uiState.asStateFlow()

    private fun setLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loading)
    }

    private fun setError(message: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = message)
    }

    private fun setHouseShares(houseShares: List<HouseShare>) {
        _uiState.value = _uiState.value.copy(houseShares = houseShares)
    }

    private fun setSelectedHouseShare(houseShare: HouseShare?) {
        _uiState.value = _uiState.value.copy(selectedHouseShare = houseShare)
    }

    fun loadHouseShare(houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { HouseSharesService.getHouseShare(houseShareId) }
            result.onSuccess { houseShare ->
                setSelectedHouseShare(houseShare)
                setLoading(false)
                setError(null)
            }.onFailure { exception ->
                setError("Failed to load house share: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun loadUsersHouseShares(userId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { HouseSharesService.getUsersHouseShares(userId) }
            result.onSuccess { houseShares ->
                setHouseShares(houseShares)
                setLoading(false)
                setError(null)
            }.onFailure { exception ->
                setError("Failed to load user's house shares: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun addHouseShare(name: String) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { HouseSharesService.addHouseShare(name) }
            result.onSuccess { newHouseShare ->
                setHouseShares(_uiState.value.houseShares + newHouseShare)
                setLoading(false)
                setError(null)
            }.onFailure { exception ->
                setError("Failed to add house share: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun addUserToHouseShare(email: String, houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { HouseSharesService.addUserToHouseShare(email, houseShareId) }
            result.onSuccess {
                loadHouseShare(houseShareId)
            }.onFailure { exception ->
                setError("Failed to add user to house share: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun editHouseShare(houseShareId: Int, name: String, imageUrl: String) {
        setLoading(true)
        viewModelScope.launch {
            val result =
                runCatching { HouseSharesService.editHouseShare(houseShareId, name, imageUrl) }
            result.onSuccess { updatedHouseShare ->
                val updatedList = _uiState.value.houseShares.map { houseShare ->
                    if (houseShare.id == houseShareId) updatedHouseShare else houseShare
                }
                setHouseShares(updatedList)
                setLoading(false)
                setError(null)
            }.onFailure { exception ->
                setError("Failed to edit house share: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun deleteHouseShare(houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { HouseSharesService.deleteHouseShare(houseShareId) }
            result.onSuccess {
                setHouseShares(_uiState.value.houseShares.filter { it.id != houseShareId })
                setLoading(false)
                setError(null)
            }.onFailure { exception ->
                setError("Failed to delete house share: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun removeUserFromHouseShare(userId: Int, houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result =
                runCatching { HouseSharesService.removeUserFromHouseShare(userId, houseShareId) }
            result.onSuccess {
                loadHouseShare(houseShareId)
            }.onFailure { exception ->
                setError("Failed to remove user from house share: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun resetSelectedHouseShare() {
        setSelectedHouseShare(null)
    }
}
