package com.example.mtrnme2.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.mtrnme2.R
import com.example.mtrnme2.models.*
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.UserService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    var userService : UserService?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userService = ServiceBuilder.buildservice()

        btn_rgstr.setOnClickListener{

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            if (checkValidations()){
                var addUserCall : Call<GenericResponse> = userService?.addUser(User(username = uname_txt.text.toString()))!!

                addUserCall.enqueue(object : Callback<GenericResponse>{
                    override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                        showToast("Username already exists!")
                    }

                    override fun onResponse(
                        call: Call<GenericResponse>,
                        response: Response<GenericResponse>
                    ) {
                        Log.e("app Network Response", "Response Body : " + response.body())

                        if (response.isSuccessful || response.body()!=null){
                            var responsebody : GenericResponse = response.body()!!
                            Log.e("app New User Response", "Response Body : " + responsebody.message)

                           if(responsebody.status == 1) {

                                val intent = Intent(this@MainActivity, UserRegistration::class.java)
                                appData.username =  uname_txt.text.toString();
                                startActivity(intent)
                            }else{
                               showToast("Username already exists!")
                           }

                        }
                    }

                })
            }else{
               // Toast.makeText(this@MainActivity, "Empty", Toast.LENGTH_SHORT).show()
            }
        }

        btn_lgn.setOnClickListener{

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            if (checkValidations()){
                var getUserCall : Call<AllUserResponseItem> = userService?.getUser(userName(username = uname_txt.text.toString()))!!
                getUserCall.enqueue(object : Callback<AllUserResponseItem>{
                    override fun onFailure(call: Call<AllUserResponseItem>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Error Occurred : ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<AllUserResponseItem>,
                        response: Response<AllUserResponseItem>
                    ) {
                        Log.e("app Network Response", "Response Body : " + response.body())

                        if (response.isSuccessful && response.body()!=null){
                            val responsebody : AllUserResponseItem = response.body()!!
                            Log.e("app New User Response", "Response Body : " + responsebody.username)
                            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                            intent.putExtra("uname", uname_txt.text.toString())

                            if (responsebody.username != null) {
                                appData.username =  responsebody.username;
                                appData.musician = responsebody.isMusician;
                                appData.name = responsebody.name;
                                startActivity(intent)
                            }
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


