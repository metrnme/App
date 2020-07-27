package com.example.mtrnme2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.mtrnme2.R
import com.example.mtrnme2.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item->
        when(item.itemId){
            R.id.Home_ic->{
                replaceFragment(PlayerFragment())
                Log.d("app:Print1", "Home Is Pressed")
                return@OnNavigationItemSelectedListener true
            }
            R.id.Search_ic->{
                replaceFragment(SearchFragment())
                Log.d("app:Print1", "Search Is Pressed")
                return@OnNavigationItemSelectedListener true
            }
            R.id.Library_ic->{
                replaceFragment(TrackFragment())
                Log.d("app:Print1", "Track Fragment Is Pressed")
                return@OnNavigationItemSelectedListener true
               //val intent = Intent(this@DashboardActivity, TrackActivity::class.java)
               // startActivity(intent)
               // return@OnNavigationItemSelectedListener true
            }

            R.id.Settings_ic->{
                replaceFragment(SettingsFragment())
                Log.d("app:Print1", "Settings Is Pressed")
                return@OnNavigationItemSelectedListener true
            }

        }
        false

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        btm_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}

