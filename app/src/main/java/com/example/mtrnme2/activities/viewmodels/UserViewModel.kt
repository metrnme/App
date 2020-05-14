package com.example.mtrnme2.activities.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mtrnme2.models.User

class UserViewModel:ViewModel() {
    fun getUsers(): MutableList<User> {
        var listOfUsers = mutableListOf<User>()
        listOfUsers.add(User("meep1", "something"))
        listOfUsers.add(User("skrill", "something12"))
        listOfUsers.add(User("6ix", "something21"))
        listOfUsers.add(User("xae-12", "something2"))
        listOfUsers.add(User("kys", "something3"))


        return listOfUsers
    }
}