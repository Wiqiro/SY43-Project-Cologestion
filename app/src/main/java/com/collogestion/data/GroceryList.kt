package com.collogestion.data

import com.google.gson.annotations.SerializedName

class GroceryList(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("assignee_id") var assigneeId: Int,
    @SerializedName("house_share_id") var houseShareId: Int,
    @SerializedName("items") var items: List<GroceryListItem>
)

