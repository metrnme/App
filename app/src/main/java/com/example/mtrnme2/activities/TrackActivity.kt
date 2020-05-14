package com.example.mtrnme2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtrnme2.R
import com.example.mtrnme2.fragments.TrackFragment

/*
* This is a track activity. You can do any network calls here and then transfer them to fragment
* */
class TrackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.track_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TrackFragment.newInstance())
                .commitNow()
        }
    }
}
