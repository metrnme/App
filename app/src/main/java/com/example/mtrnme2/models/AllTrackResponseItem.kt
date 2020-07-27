package com.example.mtrnme2.models

data class AllTrackResponseItem(
    val track_id: Int,
    val name: String,
    val username: String,
    val url: String,
    val likes: Int
)