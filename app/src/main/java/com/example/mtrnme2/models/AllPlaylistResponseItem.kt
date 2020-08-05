package com.example.mtrnme2.models

data class AllPlaylistResponseItem(
    val _id: Id,
    val name: String,
    val p_type: String,
    val pl_id: Int,
    val timestamp: Timestamp,
    val track_list: ArrayList<Int>,
    val username: String
)