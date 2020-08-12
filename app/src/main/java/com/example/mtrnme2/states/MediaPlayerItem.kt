package com.example.mtrnme2.states


data class MediaData(
    var url : String?=null,
    var state : PlayerState = PlayerState.UNKNOWN
)

abstract interface data{
    fun state() : PlayerState
    fun mediaPlayerURL() : String
}