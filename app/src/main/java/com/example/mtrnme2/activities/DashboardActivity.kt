package com.example.mtrnme2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mtrnme2.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item->
        when(item.itemId){
            R.id.Home_ic->{
                Log.d("app:Print1", "Home Is Pressed")
                return@OnNavigationItemSelectedListener true
            }
            R.id.Search_ic->{
                Log.d("app:Print1", "Search Is Pressed")
                return@OnNavigationItemSelectedListener true
            }
            R.id.Library_ic->{
                Log.d("app:Print1", "Library Is Pressed")
                return@OnNavigationItemSelectedListener true
            }

        }
        false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btm_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
}

