package com.collogestion.data

import com.google.gson.annotations.SerializedName
import java.util.Date

class HouseShare(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("creation_date") private var _creationDate: Int,
    @SerializedName("users") var users: List<User>
) {
    val creationDate: Date
        get() = Date(_creationDate.toLong() * 1000)
}