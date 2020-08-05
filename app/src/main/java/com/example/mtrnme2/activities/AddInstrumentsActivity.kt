package com.example.mtrnme2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mtrnme2.R
import com.example.mtrnme2.fragments.InstrumentFragment

class AddInstrumentsActivity : BaseActivity() {
    var uname: String? = appData.username
    var usertype: Boolean? = appData.musician


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_instruments)
            uname = appData.username
            usertype = appData.musician
            if (!usertype!!){
               startActivity(Intent(this@AddInstrumentsActivity, DashboardActivity::class.java))
            }
            Toast.makeText(this, uname, Toast.LENGTH_SHORT).show()
            if (uname == null) {
                return
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
