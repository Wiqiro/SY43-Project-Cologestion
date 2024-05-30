package com.collogestion.ui.due

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collogestion.data.Due
import com.collogestion.network.DuesService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DueUiState(
    val dues: List<Due> = emptyList(),
    val selectedDueId: Int? = null,
    val selectedDue: Due? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class DueViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DueUiState())
    val uiState: StateFlow<DueUiState> = _uiState.asStateFlow()

    private fun setLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loading)
    }

    private fun setError(message: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = message)
    }

    private fun setDues(dues: List<Due>) {
        _uiState.value = _uiState.value.copy(dues = dues)
    }

    private fun setSelectedDue(due: Due?) {
        _uiState.value = _uiState.value.copy(selectedDue = due)
    }

    fun loadHouseShareDues(houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { DuesService.getHouseShareDues(houseShareId) }
            result.onSuccess { dues ->
                setDues(dues)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to load house share dues: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun loadUserDues(userId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { DuesService.getUserDues(userId) }
            result.onSuccess { dues ->
                setDues(dues)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to load user dues: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun addDue(amount: Double, creditorId: Int, debtorId: Int, houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { DuesService.addDue(amount, creditorId, debtorId, houseShareId) }
            result.onSuccess { newDue ->
                setDues(_uiState.value.dues + newDue)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to add due: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun editDue(dueId: Int, amount: Double, creditorId: Int, debtorId: Int, houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { DuesService.editDue(dueId, amount, creditorId, debtorId, houseShareId) }
            result.onSuccess { updatedDue ->
                val updatedList = _uiState.value.dues.map { due ->
                    if (due.id == dueId) updatedDue else due
                }
                setDues(updatedList)
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to edit due: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun deleteDue(dueId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { DuesService.deleteDue(dueId) }
            result.onSuccess {
                setDues(_uiState.value.dues.filter { it.id != dueId })
                setLoading(false)
            }.onFailure { exception ->
                setError("Failed to delete due: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun selectDue(dueId: Int) {
        val selectedDue = _uiState.value.dues.find { it.id == dueId }
        _uiState.value = _uiState.value.copy(
            selectedDueId = dueId,
            selectedDue = selectedDue
        )
    }

    fun clearError() {
        setError(null)
    }
}
