package com.example.mtrnme2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtrnme2.R
import com.example.mtrnme2.services.UserService

class SelectUserType : AppCompatActivity() {
    var uname : String?=null
    var userService : UserService?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_user_type)
    }
}
