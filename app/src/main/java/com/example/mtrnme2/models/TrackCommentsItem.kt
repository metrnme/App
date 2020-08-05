package com.example.mtrnme2.models

data class TrackCommentsItem(
    val _id: Id,
    val content: String,
    val timestamp: Timestamp,
    val track_id: Int,
    val username: String,
    val vote: Int
)