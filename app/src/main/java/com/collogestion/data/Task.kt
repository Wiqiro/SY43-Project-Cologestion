package com.collogestion.data

import com.google.gson.annotations.SerializedName

class Task(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("deadline") var deadline: Int,
    @SerializedName("done") var done: Boolean,
    @SerializedName("assignee_id") var assignee: Int,
    @SerializedName("house_share_id") var houseShareId: Int
)