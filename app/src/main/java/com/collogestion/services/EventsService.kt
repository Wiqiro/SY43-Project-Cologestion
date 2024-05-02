package com.collogestion.services

import com.collogestion.data.Event
import okhttp3.FormBody
import java.util.Date

object EventsService {
    fun getHouseShareEvents(houseShareId: Int, completion: (List<Event>) -> Unit) {
        HttpClient.getRequest("/events/house_share/$houseShareId") { response ->
            val events = HttpClient.gson.fromJson(response, Array<Event>::class.java).toList()
            completion(events)
        }
    }

    fun addEvent(
        date: Date, title: String, duration: Int, houseShareId: Int, completion: (Event) -> Unit
    ) {
        val body = mapOf(
            "date" to date,
            "title" to title,
            "duration" to duration,
            "house_share_id" to houseShareId
        )
        HttpClient.postRequest("/events", body) { response ->
            val event = HttpClient.gson.fromJson(response, Event::class.java)
            completion(event)
        }
    }

    fun editEvent(
        eventId: Int,
        date: Date,
        title: String,
        duration: Int,
        houseShareId: Int,
        completion: (Event) -> Unit
    ) {
        val body = mapOf(
            "date" to date,
            "title" to title,
            "duration" to duration,
            "house_share_id" to houseShareId
        )
        HttpClient.putRequest("/events/$eventId", body) { response ->
            val event = HttpClient.gson.fromJson(response, Event::class.java)
            completion(event)
        }
    }

    fun deleteEvent(eventId: Int, completion: (Event) -> Unit) {
        HttpClient.deleteRequest("/events/$eventId") { response ->
            val event = HttpClient.gson.fromJson(response, Event::class.java)
            completion(event)
        }
    }
}