package com.collogestion.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Event(
    @SerializedName("id") var id: Int,
    @SerializedName("date") private var _date: Int,
    @SerializedName("title") var title: String,
    @SerializedName("duration") var duration: Int,
    @SerializedName("house_share_id") var houseShareId: Int
) {
    val date: Date
        get() = Date(_date.toLong() * 1000)
}