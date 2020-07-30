package com.example.mtrnme2.utils
import com.preference.PowerPreference

class SessionManager() : SessionHelper {
    var prefs = PowerPreference.getDefaultFile()
    override var userName: String
        get() = prefs.getString(Constants.userName)
        set(value) {prefs.putString(Constants.userName, value)}
    override var userPassword: String
        get() = prefs.getString(Constants.userPassword)
        set(value) {prefs.putString(Constants.userPassword, value)}
    override var userEmail: String
        get() = prefs.getString(Constants.userEmail)
        set(value) {prefs.putString(Constants.userEmail, value)}
    override var userID: String
        get() = prefs.getString(Constants.userID)
        set(value) {prefs.putString(Constants.userID, value)}
}