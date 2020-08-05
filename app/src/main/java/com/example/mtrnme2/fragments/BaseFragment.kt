package com.example.mtrnme2.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mtrnme2.utils.AppDataManager

open class BaseFragment : Fragment() {
    var appData = AppDataManager()
    fun showToast(message : String){
        Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
    }
}