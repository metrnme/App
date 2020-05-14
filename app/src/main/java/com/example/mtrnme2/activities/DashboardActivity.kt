package com.example.mtrnme2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.mtrnme2.R
import com.example.mtrnme2.fragments.HomeFragment
import com.example.mtrnme2.fragments.SearchFragment
import com.example.mtrnme2.fragments.TrackFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item->
        when(item.itemId){
            R.id.Home_ic->{
                replaceFragment(HomeFragment())
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
            }

        }
        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btm_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}

