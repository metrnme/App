package com.example.mtrnme2.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mtrnme2.R
import com.example.mtrnme2.activities.DashboardActivity
import com.example.mtrnme2.activities.MainActivity
import com.example.mtrnme2.activities.SelectUserType
import com.example.mtrnme2.models.AllUserResponse
import com.example.mtrnme2.models.AllUserResponseItem
import com.example.mtrnme2.models.UserInfoResponse
import com.example.mtrnme2.models.userName
import com.example.mtrnme2.services.UserService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        var User : ArrayList<AllUserResponseItem> = ArrayList<AllUserResponseItem>()
//        var userService : UserService?=null
//        var getUserCall : Call<AllUserResponseItem> = userService?.getUser(userName(username = appData.username!!))!!
//        getUserCall.enqueue(object : Callback<AllUserResponseItem> {
//            override fun onFailure(call: Call<AllUserResponseItem>, t: Throwable) {
//            }
//            override fun onResponse(
//                    call: Call<AllUserResponseItem>,
//                    response: Response<AllUserResponseItem>
//            ) {
//                Log.e("app Network Response", "Response Body : " + response.body())
//                var responsebody : AllUserResponseItem = response.body()!!
//                User.add(responsebody)
//            }
//        })

        utype_tv.setOnClickListener {

            val utype = Intent(activity, SelectUserType::class.java)
            startActivity(utype)
        }

        my_profile.setOnClickListener {
            showToast("Clicked me!")
//                        var navigator = findNavController()
//                        assert(navigator!=null)
//                        var bundle = Bundle()
//                        bundle.putString("data", Gson().toJson(User[0], AllUserResponseItem::class.java))
//                        navigator.navigate(R.id.nav_profile, bundle)
        }

        tv_about.setOnClickListener {
            Toast.makeText(this.context, "about", Toast.LENGTH_SHORT).show()
        }

        btn_logout.setOnClickListener {
            val u = Intent(activity, MainActivity::class.java)
            startActivity(u)
        }
    }
}