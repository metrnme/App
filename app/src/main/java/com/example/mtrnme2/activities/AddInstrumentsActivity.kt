package com.example.mtrnme2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mtrnme2.R
import com.example.mtrnme2.fragments.InstrumentFragment

class AddInstrumentsActivity : AppCompatActivity() {
    var uname: String? = null
    var usertype: Boolean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_instruments)
        /*Here we are loading fragment in a FrameLayout*/

        // Before loading fragment , you can do anything like add logic to whether load data from network or database
        if (intent.extras!=null) {
            uname = intent?.extras?.getString("uname", null)
            usertype = intent?.extras?.getBoolean("usertype", false)
            if (!usertype!!){
               // startActivity(Intent(this@AddInstrumentsActivity, DashboardActivity::class.java))
            }

            Toast.makeText(this, uname, Toast.LENGTH_SHORT).show()
            if (uname == null) {
                return
            }
        }

        if (savedInstanceState == null) {
            /*var fragment = InstrumentFragment.newInstance();
            var b = Bundle();
            b.putString("username", username)
            fragment.arguments = b*/

            supportFragmentManager.beginTransaction()
                .replace(R.id.icontainer, InstrumentFragment.newInstance())
                .commitNow()
        }
    }
}
