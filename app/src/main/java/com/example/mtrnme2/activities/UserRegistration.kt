package com.example.mtrnme2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mtrnme2.R
import com.example.mtrnme2.models.NewUserResponse
import com.example.mtrnme2.models.User
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.UserService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRegistration : AppCompatActivity() {

    var uname : String?=null
    var userService : UserService?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        if (intent.extras!=null){
            uname = intent?.extras?.getString("uname", null)
            Toast.makeText(this,uname,Toast.LENGTH_SHORT).show()
            if (uname==null){
                return
            }
            userService = ServiceBuilder.buildservice()
            btn_nxt_rgstr.setOnClickListener{
            //We add bio information over here.
            if(checkValidations()) {
                    val update =User(username = uname!!,name= name_txt.text.toString(), age= age_txt.text.toString().toInt() ,gender= gender_txt.text.toString())
                    var updateUserInfo : Call<NewUserResponse> = userService?.updateUserInfo(update)!!
                    updateUserInfo.enqueue(object : Callback<NewUserResponse>{
                        override fun onFailure(call: Call<NewUserResponse>, t: Throwable) {
                            Toast.makeText(this@UserRegistration, "Error Occurred : ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(
                            call: Call<NewUserResponse>,
                            response: Response<NewUserResponse>
                        ) {
                            Log.e("app:Network Response", "Response Body : " + response.errorBody())
                            if (response.isSuccessful || response.body()!=null){
                                var responsebody : NewUserResponse = response.body()!!
                                Log.e("app:User Info Response", "Response Body : " + responsebody.message)

                                val intent = Intent(this@UserRegistration, SelectUserType::class.java)
                                intent.putExtra("uname", uname)
                                startActivity(intent)
                            }
                        }
                    });
                }

            }


        }

    }
    private fun checkValidations(): Boolean {
        if (name_txt.text.isNullOrEmpty() or gender_txt.text.isNullOrEmpty() or age_txt.text.isNullOrEmpty()) {
            return false
        }
        return true
    }
}
