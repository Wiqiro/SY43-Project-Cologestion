package com.collogestion.ui.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.collogestion.data.Event
import com.collogestion.network.EventsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

data class EventsUiState(
    val events: List<Event> = emptyList(),
    val selectedEventId: Int? = null,
    val selectedEvent: Event? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class EventViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    private fun setLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loading)
    }

    private fun setError(message: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = message)
    }

    private fun setEvents(events: List<Event>) {
        _uiState.value = _uiState.value.copy(events = events)
    }

    private fun setSelectedEvent(event: Event?) {
        _uiState.value = _uiState.value.copy(selectedEvent = event)
    }

    fun loadHouseShareEvents(houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { EventsService.getHouseShareEvents(houseShareId) }
            result.onSuccess { events ->
                setEvents(events)
                setLoading(false)
                setError(null)
            }.onFailure { exception ->
                setError("Failed to load house share events: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun addEvent(date: Date, title: String, duration: Int, houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { EventsService.addEvent(date, title, duration, houseShareId) }
            result.onSuccess { newEvent ->
                setEvents(_uiState.value.events + newEvent)
                setLoading(false)
                setError(null)
            }.onFailure { exception ->
                setError("Failed to add event: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun editEvent(eventId: Int, date: Date, title: String, duration: Int, houseShareId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching {
                EventsService.editEvent(
                    eventId,
                    date,
                    title,
                    duration,
                    houseShareId
                )
            }
            result.onSuccess { updatedEvent ->
                val updatedList = _uiState.value.events.map { event ->
                    if (event.id == eventId) updatedEvent else event
                }
                setEvents(updatedList)
                setLoading(false)
                setError(null)
            }.onFailure { exception ->
                setError("Failed to edit event: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun deleteEvent(eventId: Int) {
        setLoading(true)
        viewModelScope.launch {
            val result = runCatching { EventsService.deleteEvent(eventId) }
            result.onSuccess {
                setEvents(_uiState.value.events.filter { it.id != eventId })
                setLoading(false)
                setError(null)
            }.onFailure { exception ->
                setError("Failed to delete event: ${exception.message}")
                setLoading(false)
            }
        }
    }

    fun selectEvent(eventId: Int) {
        val selectedEvent = _uiState.value.events.find { it.id == eventId }
        _uiState.value = _uiState.value.copy(
            selectedEventId = eventId,
            selectedEvent = selectedEvent
        )
    }

    fun clearSelectedEvent() {
        _uiState.value = _uiState.value.copy(
            selectedEventId = null,
            selectedEvent = null
        )
    }
}
