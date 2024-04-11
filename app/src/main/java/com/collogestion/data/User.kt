package com.collogestion.data

class User(
    var id: Int,
    var firstname: String,
    var lastname: String,
    var profilePicture: Int,
    var mail: String,
    var phone: String,
    var password: String,
    var spendings : List<Due>
)