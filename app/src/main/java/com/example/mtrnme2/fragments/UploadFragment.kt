package com.example.mtrnme2.fragments

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.abdeveloper.library.MultiSelectDialog
import com.abdeveloper.library.MultiSelectModel
import com.amplifyframework.core.Amplify
import com.developer.filepicker.model.DialogConfigs
import com.developer.filepicker.model.DialogProperties
import com.developer.filepicker.view.FilePickerDialog
import com.example.mtrnme2.R
import com.example.mtrnme2.databinding.FragmentUploadBinding
import com.example.mtrnme2.models.*
import com.example.mtrnme2.services.GenreService
import com.example.mtrnme2.services.PlaylistService
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import com.google.android.material.snackbar.Snackbar
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import kotlinx.android.synthetic.main.fragment_upload.*
import kotlinx.coroutines.delay
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
    var selectedGenre = ""
    var sGenre : Boolean = false
    var sImage : Boolean = false
    var sAudio : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        properties.selection_type = DialogConfigs.FILE_SELECT
        dialog = FilePickerDialog(this.context, properties)
        trackService = ServiceBuilder.buildTrackService()

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

          binding.btnGenre.setOnClickListener {
              //Add to playlist

              var listOfGenre = mutableListOf<AllGenreResponseItem>()
              var genreService: GenreService? = null
              genreService = ServiceBuilder.buildGenreService()
              var getAllGenress: Call<AllGenreResponse> = genreService.getAllGenre()!!
              getAllGenress.enqueue(object : Callback<AllGenreResponse> {
                  override fun onFailure(call: Call<AllGenreResponse>, t: Throwable) {
                      Log.e("app:", "Error Occurred : ${t.message}")
                  }

                  override fun onResponse(
                      call: Call<AllGenreResponse>,
                      response: Response<AllGenreResponse>
                  ) {
                      // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                      if (response.isSuccessful || response.body() != null) {
                          var responsebody: AllGenreResponse = response.body()!!
                          Log.e(
                              "app:Track Info Response",
                              "Response Body : $responsebody"
                          )
                          //Should get all Instruments from here

                          val genre = responsebody

                          showLog(genre.size.toString() + " Size")

                          val items = ArrayList<SheetSelectionItem>()

                          for(list in genre){
                              items.add(SheetSelectionItem("", list.name))
                          }
                          SheetSelection.Builder(context!!)
                              .title("Choose Genre")
                              .items(items)
                              .selectedPosition(2)
                              .showDraggedIndicator(true)
                              .searchEnabled(true)
                              .onItemClickListener { _, GenrePosition ->
                                  //addtoPlaylist(item.key.toInt(), listOfTracks[position].track_id)
                                  selectedGenre=items[GenrePosition].value
                                  showToast(items[GenrePosition].value)
                                  sGenre = true
                              }
                              .show()
                      }
                  }
              })
          }
//        binding.btnGenre.setOnClickListener{
//           var listOfGenre = ArrayList<MultiSelectModel>()
//            listOfGenre.add(MultiSelectModel(1,"Rock"))
//            listOfGenre.add(MultiSelectModel(1,"House"))
//            listOfGenre.add(MultiSelectModel(1,"Classical"))
//
//            var alreadySelectedGenre = ArrayList<Int>()
//            val multiSelectDialog: MultiSelectDialog = MultiSelectDialog()
//                    .title("Genre") //setting title for dialog
//                    .titleSize(25F)
//                    .positiveText("Done")
//                    .negativeText("Cancel")
//                    .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
//                    .setMaxSelectionLimit(listOfGenre.size) //you can set maximum checkbox selection limit (Optional)
//                    .preSelectIDsList(alreadySelectedGenre) //List of ids that you need to be selected
//                    .multiSelectList(listOfGenre) // the multi select model list with ids and name
//                    .onSubmit(object : MultiSelectDialog.SubmitCallbackListener {
//                        override fun onSelected(selectedIds: ArrayList<Int?>, selectedNames: ArrayList<String?>, dataString: String) {
//                            //will return list of selected IDS
//                            for (i in selectedIds) {
//                                showToast(i.toString())
//                                showToast(i.toString())
//                                showToast(dataString)
//                            }
//                        }
//
//                        override fun onCancel() {
//                            Log.d(TAG, "Dialog cancelled")
//                        }
//                    })
////            multiSelectDialog.show(getSupportFragmentManager(),"multiSelectDialog")
//
//        }

        binding.btnBrowseart.setOnClickListener { view ->

            dialog.setDialogSelectionListener {


                Toast.makeText(
                    this.context,
                    "File Selected: ${it.size} has been selected",
                    Toast.LENGTH_SHORT
                ).show()

                sImage = true
                var date = java.util.Calendar.getInstance()
                imgKey="${date.time.time}_"+(0..100).random().toString()+"_profile_image"

                uploadFile(imgKey,it[0].toString())

            }
            dialog.setTitle("Select an image File")
            dialog.show()

        }

        binding.btnBrowseaudio.setOnClickListener { view ->
            dialog.setDialogSelectionListener {


                Toast.makeText(
                    this.context,
                    "File Selected: ${it.size} has been selected",
                    Toast.LENGTH_SHORT
                ).show()


                var date = java.util.Calendar.getInstance()
                trackKey="${date.time.time}_"+(0..100).random().toString()+"_track"
                showToast("File being uploaded")
                showToast("Please press upload button when file is uploaded")
                uploadFile(trackKey,it[0].toString())
                sAudio = true
            }
            dialog.setTitle("Select a File")
            dialog.show()

        }

        binding.fabUpl.setOnClickListener { view ->
            if (binding.nameTxt.text.isNullOrEmpty()) {
                showToast("Please provide title")
                return@setOnClickListener
            }

            if (binding.instTxt.text.isNullOrEmpty()) {
                showToast("Please provide Instrument Name")
                return@setOnClickListener
            }

            if (sGenre && sAudio && sImage) {

                var addTrack: Call<GenericResponse> = trackService?.uploadTrack(
                    TrackUpload(
                        name = name_txt.text.toString(),
                        url = trackKey,
                        username = appData.username,
                        image_url = imgKey,
                        genre = listOf(selectedGenre),
                        inst_used = listOf("Drums", "Guitars", "Vocals")
                    )
                )!!
                addTrack.enqueue(object : Callback<GenericResponse> {
                    override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                        showToast("Failed to Upload Track")
                    }

                    override fun onResponse(
                        call: Call<GenericResponse>,
                        response: Response<GenericResponse>
                    ) {
                        Log.e("app Network Response", "Response Body : " + response.body())

                        if (response.isSuccessful || response.body() != null) {
                            var responsebody: GenericResponse = response.body()!!
                            Log.e("Track Uploaded", "Response Body : " + responsebody.message)

                        }
                    }

                })
                showToast("Track Has Been Uploaded!")
                findNavController().navigate(R.id.action_nav_upload_to_nav_playlist)
            }
            else{
                showToast("genre/image/audio files missing")
            }
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



