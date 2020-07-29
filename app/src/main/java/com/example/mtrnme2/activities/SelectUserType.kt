package com.example.mtrnme2.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mtrnme2.R
import com.example.mtrnme2.models.GenericResponse
import com.example.mtrnme2.models.User
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.UserService
import kotlinx.android.synthetic.main.activity_select_user_type.*
import kotlinx.android.synthetic.main.activity_user_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SelectUserType : AppCompatActivity() {
    var uname: String? = null
    var userService: UserService? = null
    var usertype= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_user_type)
        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                if(radio.text=="Musician"){
                    usertype=true
                }
                if(radio.text=="Listener"){
                    usertype=false
                }
                Toast.makeText(applicationContext," On checked change : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
            })
        addListenerOnButton()
    }
    private fun radio_button_click(view: View){
        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(radioGroup.checkedRadioButtonId)

    }

    private fun addListenerOnButton(){
        if (intent.extras!=null) {
            uname = intent?.extras?.getString("uname", null)
            Toast.makeText(this, uname, Toast.LENGTH_SHORT).show()
            if (uname == null) {
                return
            }

            var id: Int = radioGroup.checkedRadioButtonId
            if (id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using id
                val radio:RadioButton = findViewById(id)

                if(radio.text=="Musician"){
                    usertype=true
                }
                if(radio.text=="Listener"){
                    usertype=false
                }
                Toast.makeText(applicationContext,"On button click : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
                Log.d("Counter",usertype.toString())
                SubmitButton(usertype)
            }else{
                // If no radio button checked in this radio group
                Toast.makeText(applicationContext,"On button click : nothing selected",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun SubmitButton(type:Boolean){
        btn_done.setOnClickListener {
            //We add bio information over here.
                userService = ServiceBuilder.buildservice()
                val update = User(
                    username = uname!!,
                    usertype = type
                )
                var setUserType: Call<GenericResponse> =
                    userService?.setUserType(update)!!
                setUserType.enqueue(object : Callback<GenericResponse> {
                    override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                        Toast.makeText(
                            this@SelectUserType,
                            "Error Occurred : ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<GenericResponse>,
                        response: Response<GenericResponse>
                    ) {
                        Log.e("app:Network Response", "Response Body : " + response.errorBody())
                        if (response.isSuccessful || response.body() != null) {
                            var responsebody: GenericResponse = response.body()!!
                            Log.e(
                                "app:User Info Response",
                                "Response Body : " + responsebody.message
                            )
                            intent.putExtra("uname", uname)
                            intent.putExtra("usertype",type)
                            if(usertype==true) {
                                val intent =
                                    Intent(this@SelectUserType, AddInstrumentsActivity::class.java)

                                startActivity(intent)
                            }else{

                                val intent2 =
                                    Intent(this@SelectUserType, DashboardActivity::class.java)
                                startActivity(intent2)
                            }
                        }
                    }
                })
            }
        }
}
