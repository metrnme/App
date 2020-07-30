package com.example.mtrnme2.models

data class AllPlaylistResponseItem(
    val _id: IdX,
    val name: String,
    val p_type: String,
    val pl_id: Int,
    val timestamp: TimestampX,
    val track_list: List<Int>,
    val username: String
)