package com.example.mtrnme2.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mtrnme2.R
import com.example.mtrnme2.fragments.InstrumentFragment
import com.example.mtrnme2.services.UserService
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_select_user_type.*


class SelectUserType : AppCompatActivity() {
    var uname: String? = null
    var userService: UserService? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_user_type)

        btn_done.setOnClickListener {
            val intent = Intent(this@SelectUserType, AddInstrumentsActivity::class.java)
            startActivity(intent)
        }
    }

}
