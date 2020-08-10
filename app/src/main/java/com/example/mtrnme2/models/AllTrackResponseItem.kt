package com.example.mtrnme2.models

data class AllTrackResponseItem(
    val track_id: Int,
    val name: String,
    val username: String,
    val url: String,
    val likes: Int,
    val genre: List<String>,
    val image_url: String,
    val inst_used: List<String>,
    val user_likes: ArrayList<String>
    )