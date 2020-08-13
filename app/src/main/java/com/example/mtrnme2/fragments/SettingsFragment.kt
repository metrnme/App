package com.example.mtrnme2.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mtrnme2.R
import com.example.mtrnme2.activities.MainActivity
import com.example.mtrnme2.activities.SelectUserType
import kotlinx.android.synthetic.main.fragment_settings.*


@Suppress("DEPRECATION")
class SettingsFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        utype_tv.setOnClickListener {

            val utype = Intent(activity, SelectUserType::class.java)
            startActivity(utype)
        }

        my_profile.setOnClickListener {

            findNavController().navigate(R.id.action_nav_settings_to_userProfileFragment)
        }

//        tv_about.setOnClickListener {
//            Toast.makeText(this.context, "about", Toast.LENGTH_SHORT).show()
//        }

        btn_logout.setOnClickListener {
            val u = Intent(activity, MainActivity::class.java)
            startActivity(u)
            showToast("Logged Out")
        }
    }
}