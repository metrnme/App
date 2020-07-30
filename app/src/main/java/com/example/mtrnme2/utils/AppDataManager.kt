package com.example.mtrnme2.utils

class AppDataManager : SessionHelper {
    private var sessionManager = SessionManager()
    override var userName: String
        get() = sessionManager.userName
        set(value) {
            sessionManager.userName = value
        }
    override var userPassword: String
        get() = sessionManager.userPassword
        set(value) {
            sessionManager.userPassword = value
        }
    override var userEmail: String
        get() = sessionManager.userEmail
        set(value) {
            sessionManager.userEmail = value
        }
    override var userID: String
        get() = sessionManager.userID
        set(value) {
            sessionManager.userID = value
        }

}