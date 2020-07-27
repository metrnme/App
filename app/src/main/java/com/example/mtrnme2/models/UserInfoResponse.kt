package com.example.mtrnme2.models

data class UserInfoResponse(
    val _id: Id,
    val age: Int,
    val followers: List<String>,
    val following: List<String>,
    val gender: String,
    val inst: List<Any>,
    val isMusician: Boolean,
    val name: String,
    val timestamp: Timestamp,
    val username: String
)