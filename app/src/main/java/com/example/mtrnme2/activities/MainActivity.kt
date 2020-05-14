package com.example.mtrnme2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mtrnme2.R
import com.example.mtrnme2.models.GenericResponse
import com.example.mtrnme2.models.User
import com.example.mtrnme2.models.UserInfoResponse
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.UserService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var userService : UserService?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userService = ServiceBuilder.buildservice()

        btn_rgstr.setOnClickListener{
            if (checkValidations()){
                var addUserCall : Call<GenericResponse> = userService?.addUser(User(username = uname_txt.text.toString()))!!

                addUserCall.enqueue(object : Callback<GenericResponse>{
                    override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Error Occurred : ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<GenericResponse>,
                        response: Response<GenericResponse>
                    ) {
                        Log.e("app Network Response", "Response Body : " + response.body())

                        if (response.isSuccessful || response.body()!=null){
                            var responsebody : GenericResponse = response.body()!!
                            Log.e("app New User Response", "Response Body : " + responsebody.message)

                            val intent = Intent(this@MainActivity, UserRegistration::class.java)
                            intent.putExtra("uname", uname_txt.text.toString())
                            startActivity(intent)
                        }
                    }

                })
            }else{
                Toast.makeText(this@MainActivity, "Empty", Toast.LENGTH_SHORT).show()
            }
        }

        btn_lgn.setOnClickListener{
            if (checkValidations()){
                var getUserCall : Call<UserInfoResponse> = userService?.getUser(uname_txt.text.toString())!!

                getUserCall.enqueue(object : Callback<UserInfoResponse>{
                    override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Error Occurred : ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<UserInfoResponse>,
                        response: Response<UserInfoResponse>
                    ) {
                        Log.e("app Network Response", "Response Body : " + response.body())

                        if (response.isSuccessful || response.body()!=null){
                            val responsebody : UserInfoResponse = response.body()!!
                            Log.e("app New User Response", "Response Body : " + responsebody.username)
                            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                            intent.putExtra("uname", uname_txt.text.toString())
                            startActivity(intent)
                        }
                    }

                })
            }
        }
    }
    private fun checkValidations(): Boolean {
        if (uname_txt.text.isNullOrEmpty()){
            Toast.makeText(this@MainActivity, "Username is empty!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}


