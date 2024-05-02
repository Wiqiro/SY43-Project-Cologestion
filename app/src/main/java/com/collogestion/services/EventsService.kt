package com.collogestion.services

import com.collogestion.data.Event

object EventsService {
    fun getHouseShareEvents(houseShareId: Int, completion: (List<Event>) -> Unit) {
        HttpClient.getRequest("/events/house_share/$houseShareId") { response ->
            val events = HttpClient.gson.fromJson(response, Array<Event>::class.java).toList()
            completion(events)
        }
    }
}