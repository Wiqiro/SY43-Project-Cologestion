package com.collogestion.data

import com.google.gson.annotations.SerializedName
import java.util.Date

class Task(
    @SerializedName("id") var id : String,
    @SerializedName("name") var name : String,
    @SerializedName("deadline") var deadline : Date,
    @SerializedName("done") var done : Boolean,
    @SerializedName("assignee_id") var assignee : Int,
    @SerializedName("house_share_id") var houseShareId : Int
)