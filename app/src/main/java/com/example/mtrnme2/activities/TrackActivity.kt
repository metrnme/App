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

/*
* This is a dummy track activity. You can do any network calls here and then transfer them to fragment
* */
class TrackActivity : AppCompatActivity() {
    var TrackService: TrackService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.track_activity)
        /*Here we are loading fragment in a FrameLayout*/

        // Before loading fragment , you can do anything like add logic to whether load data from network or datababase. s3 bucket to get the mp3 file can be done here?No  It will be done in fragment got

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TrackFragment.newInstance())
                .commitNow()
        }
    }
}
