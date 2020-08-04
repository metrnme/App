package com.example.mtrnme2.utils

class AppDataManager : SessionHelper {
    private var sessionManager = SessionManager()
    override var username: String
        get() = sessionManager.username
        set(value) {
            sessionManager.username = value
        }
    override var name: String
        get() = sessionManager.name
        set(value) {
            sessionManager.name = value
        }
    override var musician: Boolean
        get() = sessionManager.musician
        set(value) {
            sessionManager.musician = value
        }

}