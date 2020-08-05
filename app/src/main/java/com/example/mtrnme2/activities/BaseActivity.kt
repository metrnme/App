package com.example.mtrnme2.activities

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mtrnme2.utils.AppDataManager

open class BaseActivity : AppCompatActivity(){
    var appData = AppDataManager()
    fun showToast(message : String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}