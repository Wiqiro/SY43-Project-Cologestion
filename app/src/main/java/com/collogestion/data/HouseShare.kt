package com.collogestion.data

import java.util.Date

class HouseShare (
    var id : Int,
    var name : String,
    var image : Int,
    var creationDate : Date,
    var tasks : List<Task>,
    var groceryLists : List<GroceryList>,
    var users : List<User>
)