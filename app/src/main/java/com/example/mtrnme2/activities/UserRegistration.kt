package com.example.mtrnme2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtrnme2.R
import kotlinx.android.synthetic.main.activity_user_registration.*

class UserRegistration : AppCompatActivity() {

    var uname : String?=null
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        if (intent.extras!=null){
            uname = intent?.extras?.getString("uname", null)

            if (uname==null){
                return
            }
            //We add bio information over here.

        }

        btn_nxt_rgstr.setOnClickListener{
            val intent = Intent(this, SelectUserType::class.java)
            startActivity(intent)
        }
    }
}
