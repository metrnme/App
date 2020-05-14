package com.example.mtrnme2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mtrnme2.R
import com.example.mtrnme2.fragments.InstrumentFragment

class AddInstrumentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_instruments)
        /*Here we are loading fragment in a FrameLayout*/

        // Before loading fragment , you can do anything like add logic to whether load data from network or datababase. s3 bucket to get the mp3 file can be done here?No  It will be done in fragment got

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, InstrumentFragment.newInstance())
                .commitNow()
        }
    }
}
