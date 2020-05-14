package com.example.mtrnme2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mtrnme2.R
import com.example.mtrnme2.fragments.InstrumentFragment
import com.example.mtrnme2.services.UserService
import kotlinx.android.synthetic.main.activity_select_user_type.*

class SelectUserType : AppCompatActivity() {
    var uname: String? = null
    var userService: UserService? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_user_type)

        btn_done.setOnClickListener {
            replaceFragment(InstrumentFragment())
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}
