package com.example.mtrnme2.fragments

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.abdeveloper.library.MultiSelectDialog
import com.abdeveloper.library.MultiSelectModel
import com.amplifyframework.core.Amplify
import com.developer.filepicker.model.DialogConfigs
import com.developer.filepicker.model.DialogProperties
import com.developer.filepicker.view.FilePickerDialog
import com.example.mtrnme2.databinding.FragmentUploadBinding
import com.example.mtrnme2.models.GenericResponse
import com.example.mtrnme2.models.TrackUpload
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_upload.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.random.Random


class UploadFragment : BaseFragment() {
    private lateinit var binding: FragmentUploadBinding
    private lateinit var dialog: FilePickerDialog
    var properties = DialogProperties()
    var trackService : TrackService?=null
    var imgPath = ""
    var trackPath = ""
    var trackKey = ""
    var imgKey = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        properties.selection_type = DialogConfigs.FILE_SELECT
        dialog = FilePickerDialog(this.context, properties)
        trackService = ServiceBuilder.buildTrackService()

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnGenre.setOnClickListener{
           var listOfGenre = ArrayList<MultiSelectModel>()
            listOfGenre.add(MultiSelectModel(1,"Rock"))
            listOfGenre.add(MultiSelectModel(1,"House"))
            listOfGenre.add(MultiSelectModel(1,"Classical"))

            var alreadySelectedGenre = ArrayList<Int>()
            val multiSelectDialog: MultiSelectDialog = MultiSelectDialog()
                    .title("Genre") //setting title for dialog
                    .titleSize(25F)
                    .positiveText("Done")
                    .negativeText("Cancel")
                    .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                    .setMaxSelectionLimit(listOfGenre.size) //you can set maximum checkbox selection limit (Optional)
                    .preSelectIDsList(alreadySelectedGenre) //List of ids that you need to be selected
                    .multiSelectList(listOfGenre) // the multi select model list with ids and name
                    .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
                        override fun onSelected(selectedIds: ArrayList<Int?>, selectedNames: ArrayList<String?>, dataString: String) {
                            //will return list of selected IDS
                            for (i in selectedIds) {
                                showToast(i.toString())
                                showToast(i.toString())
                                showToast(dataString)
                            }
                        }

                        override fun onCancel() {
                            Log.d(TAG, "Dialog cancelled")
                        }
                    })
//            multiSelectDialog.show(getSupportFragmentManager(),"multiSelectDialog")

        }

        binding.btnBrowseart.setOnClickListener { view ->
            Snackbar.make(view, "BROWSE!", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
            dialog.setDialogSelectionListener {


                Toast.makeText(
                    this.context,
                    "File Selected: ${it.size} has been selected",
                    Toast.LENGTH_SHORT
                ).show()
                imgKey=name_txt.text.toString()
                imgKey+=(0..100000).random().toString()+"image"
                uploadFile(imgKey,it[0].toString())

            }
            dialog.setTitle("Select a File")
            dialog.show()

        }

        binding.btnBrowseaudio.setOnClickListener { view ->
            dialog.setDialogSelectionListener {


                Toast.makeText(
                    this.context,
                    "File Selected: ${it.size} has been selected",
                    Toast.LENGTH_SHORT
                ).show()
                trackKey=name_txt.text.toString()
                trackKey+=(0..100000).random().toString()
                trackPath=it[0].toString()
                uploadFile(trackKey,it[0].toString())
            }
            dialog.setTitle("Select a File")
            dialog.show()

        }

        binding.fabUpl.setOnClickListener { view ->
            var addTrack : Call<GenericResponse> = trackService?.uploadTrack(TrackUpload(name=name_txt.text.toString(), url=trackKey, username=appData.username, image_url=imgKey, genre = listOf("Psychedelic", "Funk", "Soul"), inst_used=listOf("Drums", "Guitars", "Vocals")))!!
            addTrack.enqueue(object : Callback<GenericResponse> {
                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                    showToast("Failed to Upload Track")
                }

                override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
                ) {
                    Log.e("app Network Response", "Response Body : " + response.body())

                    if (response.isSuccessful || response.body()!=null){
                        var responsebody : GenericResponse = response.body()!!
                        Log.e("Track Uploaded", "Response Body : " + responsebody.message)

                    }
                }

            })
            Snackbar.make(view, "UPLOAD!", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

    }


    private fun uploadFile(key:String,path: String) {
        val fileToUpload = File(path)
        Log.e("File Path", fileToUpload.absolutePath)
        Amplify.Storage.uploadFile(
            key,
            fileToUpload,
            { result ->
                Toast.makeText(
                    this.context,
                    "File SuccessFully Uploaded + ${result.getKey()}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()) },
            { error ->
                Toast.makeText(
                    this.context,
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
                        context,
                        "Permission is Required for getting list of files",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadBinding.inflate(inflater)
        return binding.root
    }


    companion object {
        fun getNewInstance(): UploadFragment {
            return UploadFragment()
        }
    }
}