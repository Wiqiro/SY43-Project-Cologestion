package com.collogestion.data

import com.google.gson.annotations.SerializedName

class HouseShare(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("creation_date") var creationDate: Int,
    @SerializedName("tasks") var tasks: List<Task>,
    @SerializedName("grocery_lists") var groceryLists: List<GroceryList>,
    @SerializedName("users") var users: List<User>
)