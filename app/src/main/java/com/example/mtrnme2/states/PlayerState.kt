package com.example.mtrnme2.states

enum class PlayerState(state : Int) {
    PLAYING(0),
    STOPPED(1),
    PAUSED(2),
    ERROR(3),
    NETWORK_UNAVAILABLE(4),
    BUFFERING(5),
    UNKNOWN(6),
    SOURCE_ATTACHED(7),
    STARTED(8),
    PREPARED(9)
}