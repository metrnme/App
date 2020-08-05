package com.example.mtrnme2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mtrnme2.R
import com.example.mtrnme2.fragments.TrackFragment
import com.example.mtrnme2.models.AllTrackResponse
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//dummy track activity to do network calls and then transfer them to fragment

class TrackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.track_activity) // Before loading fragment,
                                                // you can do anything like add logic
                                                // to whether load data from network or database.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.tcontainer, TrackFragment.newInstance())
                .commitNow()
        }
    }
}
