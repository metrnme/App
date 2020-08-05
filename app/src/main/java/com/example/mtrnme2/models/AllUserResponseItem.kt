package com.example.mtrnme2.models

data class AllUserResponseItem(
    val age: Int,
    val followers: List<Any>,
    val following: List<Any>,
    val gender: String,
    val inst: List<Any>,
    val isMusician: Boolean,
    val bio : String,
    val imgUrl : String,
    val name: String,
    val username: String
)