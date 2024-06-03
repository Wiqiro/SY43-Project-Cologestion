package com.collogestion.data

import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("id") var id: Int,
    @SerializedName("first_name") var firstname: String,
    @SerializedName("last_name") var lastname: String,
    @SerializedName("profile_picture") var profilePicture: String,
    @SerializedName("email") var mail: String,
    @SerializedName("phone") var phone: String,
    var spendings: List<Due>
)