package com.collogestion.data

import com.google.gson.annotations.SerializedName

data class GroceryListItem(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("bought") var bought: Boolean,
    @SerializedName("quantity") var quantity: Int
)
