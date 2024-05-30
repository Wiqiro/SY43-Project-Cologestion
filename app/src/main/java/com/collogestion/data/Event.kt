package com.collogestion.data

import com.google.gson.annotations.SerializedName
import java.util.Date

class Event(
    @SerializedName("id") var id : Int,
    @SerializedName("date") var date : Int,
    @SerializedName("title") var title : String,
    @SerializedName("duration") var duration : Int,
    @SerializedName("house_share_id") var houseShareId : Int
)