package com.collogestion.network

import android.util.Log
import com.collogestion.data.Event
import java.util.Date

object EventsService {
    suspend fun getHouseShareEvents(houseShareId: Int): List<Event> {
        val response = HttpClient.getRequest("/events/house_share/$houseShareId")
        return HttpClient.gson.fromJson(response, Array<Event>::class.java).toList()
    }

    suspend fun addEvent(
        date: Date, title: String, duration: Int, houseShareId: Int
    ): Event {
        val body = mapOf(
            "title" to title,
            "date" to date.time / 1000,
            "duration" to duration,
            "house_share_id" to houseShareId
        )
        Log.d("EventsService", "addEvent: $body")
        val response = HttpClient.postRequest("/events", body)
        return HttpClient.gson.fromJson(response, Event::class.java)
    }

    suspend fun editEvent(
        eventId: Int,
        date: Date,
        title: String,
        duration: Int,
        houseShareId: Int
    ): Event {
        val body = mapOf(
            "date" to date.time / 1000,
            "title" to title,
            "duration" to duration,
            "house_share_id" to houseShareId
        )
        val response = HttpClient.putRequest("/events/$eventId", body)
        return HttpClient.gson.fromJson(response, Event::class.java)
    }

    suspend fun deleteEvent(eventId: Int): Event {
        val response = HttpClient.deleteRequest("/events/$eventId")
        return HttpClient.gson.fromJson(response, Event::class.java)
    }
}
