package com.collogestion.data

import com.google.gson.annotations.SerializedName
import java.util.Date

class HouseShare (
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("image") var image : String,
    @SerializedName("creation_date") var creationDate : Int,
    @SerializedName("tasks") var tasks : List<Task>,
    @SerializedName("grocery_lists") var groceryLists : List<GroceryList>,
    @SerializedName("users") var users : List<User>
)