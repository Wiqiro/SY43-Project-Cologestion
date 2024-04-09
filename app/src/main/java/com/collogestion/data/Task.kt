package com.collogestion.data

import java.util.Date

class Task(
    var name : String,
    var deadline : Date,
    var done : Boolean,
    var users : List<User>
)