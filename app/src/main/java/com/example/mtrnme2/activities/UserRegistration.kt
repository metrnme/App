package com.example.mtrnme2.activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.amplifyframework.core.Amplify
import com.developer.filepicker.model.DialogConfigs
import com.developer.filepicker.model.DialogProperties
import com.developer.filepicker.view.FilePickerDialog
import com.example.mtrnme2.R
import com.example.mtrnme2.models.GenericResponse
import com.example.mtrnme2.models.User
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.UserService
import kotlinx.android.synthetic.main.activity_user_registration.*
import kotlinx.android.synthetic.main.activity_user_registration.name_txt
import kotlinx.android.synthetic.main.fragment_upload.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UserRegistration : BaseActivity() {

    var uname : String = appData.username;
    var userService : UserService?=null
    private lateinit var dialog: FilePickerDialog
    var properties = DialogProperties()
    var imgPath = ""
    var imgKey = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        properties.selection_type = DialogConfigs.FILE_SELECT
        dialog = FilePickerDialog(this, properties)
        setContentView(R.layout.activity_user_registration)
        btn_upload.setOnClickListener {
            dialog.setDialogSelectionListener {
                Toast.makeText(
                    this,
                    "File Selected: ${it.size} has been selected",
                    Toast.LENGTH_SHORT
                ).show()
                imgKey=name_txt.text.toString()
                imgKey+=(0..100000).random().toString()+appData.username
                uploadFile(imgKey,it[0].toString())

            }
            dialog.setTitle("Select a File")
            dialog.show()

        }

            Toast.makeText(this,uname,Toast.LENGTH_SHORT).show()
            if (uname==null){
                return
            }
            userService = ServiceBuilder.buildservice()
            btn_nxt_rgstr.setOnClickListener{
            //We add bio information over here.
            if(checkValidations()) {
                    val update =User(username = appData.username,name= name_txt.text.toString(), age= age_txt.text.toString().toInt(),bio=bio_txt.text.toString() ,gender= gender_txt.text.toString(),imgUrl = imgKey)
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

    private fun uploadFile(key:String,path: String) {
        val fileToUpload = File(path)
        Log.e("File Path", fileToUpload.absolutePath)
        Amplify.Storage.uploadFile(
            key,
            fileToUpload,
            { result ->
                Toast.makeText(
                    this,
                    "File SuccessFully Uploaded + ${result.getKey()}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()) },
            { error ->
                Toast.makeText(
                    this,
                    "Upload Failed",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("MyAmplifyApp", "Upload failed", error) }
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (dialog != null) {   //Show dialog if the read permission has been granted.
                        dialog.show()
                    }
                } else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(
                        this,
                        "Permission is Required for getting list of files",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


}
