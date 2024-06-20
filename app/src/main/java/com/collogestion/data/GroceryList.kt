package com.collogestion.data

import com.google.gson.annotations.SerializedName

data class GroceryList(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("house_share_id") var houseShareId: Int,
    @SerializedName("items") var items: List<GroceryListItem>
)

