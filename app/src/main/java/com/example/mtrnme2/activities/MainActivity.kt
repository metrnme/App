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
                var addUserCall : Call<NewUserResponse> = userService?.addUser(User(username = uname_txt.text.toString()))!!

                addUserCall.enqueue(object : Callback<NewUserResponse>{
                    override fun onFailure(call: Call<NewUserResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Error Occurred : ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<NewUserResponse>,
                        response: Response<NewUserResponse>
                    ) {
                        Log.e("app Network Response", "Response Body : " + response.body())

                        if (response.isSuccessful || response.body()!=null){
                            var responsebody : NewUserResponse = response.body()!!
                            Log.e("app New User Response", "Response Body : " + responsebody.message)

                            val intent = Intent(this@MainActivity, UserRegistration::class.java)
                            intent.putExtra("uname", uname_txt.text.toString())
                            startActivity(intent)
                        }
                    }

                })
            }


        }

        /*     btn_rgstr.setOnClickListener{
                 val newUser = User()
                 newUser.username = uname_txt.text.toString()

                 val userService:UserService = ServiceBuilder.buildservice(UserService::class.java)
                 val requestCall: Call<User> = userService.addUser(newUser)


                 requestCall.enqueue(object: Callback<User> {

                     override fun onResponse(call: Call<User>, response: Response<User>) {
                         if (response.isSuccessful) {
                             finish() // Move back to DestinationListActivity
                             var newlyCreatedDestination = response.body() // Use it or ignore it
                             Toast.makeText(applicationContext,"Successfully Added", Toast.LENGTH_SHORT).show()
                         } else {
                             Toast.makeText(applicationContext,"Failed to add item 0", Toast.LENGTH_SHORT).show()
                         }
                     }

                     override fun onFailure(call: Call<User>, t: Throwable) {
                         Toast.makeText(applicationContext, "Failed to add item 1", Toast.LENGTH_SHORT).show()
                     }
                 })
             }
     */

    }

    private fun checkValidations(): Boolean {
        if (uname_txt.text.isNullOrEmpty()){
            return false
        }

        return true
    }
}


