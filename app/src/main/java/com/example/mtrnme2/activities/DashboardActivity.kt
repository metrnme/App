package com.example.mtrnme2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.mtrnme2.R
import com.example.mtrnme2.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_upload.*
import android.view.View.GONE

class DashboardActivity : BaseActivity() {

    private var isMusician = appData.musician

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item->
        when(item.itemId){
            R.id.Home_ic->{
                findNavController(R.id.myNavHostFragment).navigate(R.id.nav_track)
                Log.d("app:Print1", "Home Is Pressed")
                return@OnNavigationItemSelectedListener true
            }
            R.id.Search_ic->{
                /*replaceFragment(SearchFragment())*/
                findNavController(R.id.myNavHostFragment).navigate(R.id.nav_search)
                Log.d("app:Print1", "Search Is Pressed")
                return@OnNavigationItemSelectedListener true
            }
            R.id.Library_ic->{
                /*replaceFragment(TrackFragment())*/
                findNavController(R.id.myNavHostFragment).navigate(R.id.nav_playlist)
                Log.d("app:Print1", "Track Fragment Is Pressed")
                return@OnNavigationItemSelectedListener true
               //val intent = Intent(this@DashboardActivity, TrackActivity::class.java)
               // startActivity(intent)
               // return@OnNavigationItemSelectedListener true
            }

            R.id.Settings_ic->{
                findNavController(R.id.myNavHostFragment).navigate(R.id.nav_settings)
                // replaceFragment(SettingsFragment())
                Log.d("app:Print1", "Settings Is Pressed")
                return@OnNavigationItemSelectedListener true
            }

        }
        false

    }

    override fun onResume() {

        super.onResume()
        if (isMusician){
            FAB.show()
        } else {
            FAB.hide()
        }
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


        val navigationController = findNavController(R.id.myNavHostFragment)
        btm_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigationController.navigate(R.id.nav_track)
        FAB.setOnClickListener{
            findNavController(R.id.myNavHostFragment).navigate(R.id.nav_upload)
        }
    }


    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }
}

