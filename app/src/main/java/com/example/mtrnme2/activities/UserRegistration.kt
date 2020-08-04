package com.example.mtrnme2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mtrnme2.R
import com.example.mtrnme2.models.GenericResponse
import com.example.mtrnme2.models.User
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.UserService
import kotlinx.android.synthetic.main.activity_user_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRegistration : BaseActivity() {

    var uname : String = appData.username;
    var userService : UserService?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)


            Toast.makeText(this,uname,Toast.LENGTH_SHORT).show()
            if (uname==null){
                return
            }
            userService = ServiceBuilder.buildservice()
            btn_nxt_rgstr.setOnClickListener{
            //We add bio information over here.
            if(checkValidations()) {
                    val update =User(username = appData.username,name= name_txt.text.toString(), age= age_txt.text.toString().toInt() ,gender= gender_txt.text.toString())
                    appData.name=name_txt.text.toString();
                    var updateUserInfo : Call<GenericResponse> = userService?.updateUserInfo(update)!!
                    updateUserInfo.enqueue(object : Callback<GenericResponse>{
                        override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                            Toast.makeText(this@UserRegistration, "Error Occurred : ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(
                            call: Call<GenericResponse>,
                            response: Response<GenericResponse>
                        ) {
                            Log.e("app:Network Response", "Response Body : " + response.errorBody())
                            if (response.isSuccessful || response.body()!=null){
                                var responsebody : GenericResponse = response.body()!!
                                Log.e("app:User Info Response", "Response Body : " + responsebody.message)

                                val intent = Intent(this@UserRegistration, SelectUserType::class.java)
                                startActivity(intent)
                            }
                        }
                    })
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
