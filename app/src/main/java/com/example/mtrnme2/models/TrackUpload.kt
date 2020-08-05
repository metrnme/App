package com.example.mtrnme2.models

data class TrackUpload(
    val name: String,
    val username: String,
    val url: String,
    val genre: List<String>,
    val image_url: String,
    val inst_used: List<String>
)