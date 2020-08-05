package com.example.mtrnme2.models

data class User(
    var username: String = "",
    var name : String = "",
    var age: Int = 0,
    var gender : String = "",
    var timestamp : String = "",
    var bio : String = "",
    var imgUrl : String = "",
    var usertype : Boolean = false,
    var followers : List<String> = emptyList<String>(),
    var following : List<String> = emptyList<String>(),
    var isMusician : Boolean = false,
    var inst : List<String> = emptyList<String>()
)