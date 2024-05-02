package com.collogestion.data

import com.google.gson.annotations.SerializedName
import java.util.Date

class HouseShare (
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("image") var image : String,
    @SerializedName("creation_date") var creationDate : Date,
    @SerializedName("id") var tasks : List<Task>,
    @SerializedName("id") var groceryLists : List<GroceryList>,
    @SerializedName("id") var users : List<User>
)

