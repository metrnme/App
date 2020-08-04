package com.example.mtrnme2.utils
import com.preference.PowerPreference

class SessionManager() : SessionHelper {
    var prefs = PowerPreference.getDefaultFile()
    override var username: String
        get() = prefs.getString(Constants.username)
        set(value) {prefs.putString(Constants.username, value)}
    override var name: String
        get() = prefs.getString(Constants.name)
        set(value) {prefs.putString(Constants.name, value)}
    override var musician: Boolean
        get() = prefs.getBoolean(Constants.musician)
        set(value) {prefs.putBoolean(Constants.musician, value)}
}