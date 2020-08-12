package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtrnme2.R
import com.example.mtrnme2.activities.viewmodels.TrackViewModel
import com.example.mtrnme2.adapters.PlaylistAdapter
import com.example.mtrnme2.models.*
import com.example.mtrnme2.services.PlaylistService
import com.example.mtrnme2.services.ServiceBuilder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_playlist.*
import kotlinx.android.synthetic.main.fragment_playlist.FAB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlaylistFragment : BaseFragment() {
    //This is an instance of Track Adapter

    var playAdapter: PlaylistAdapter? = null


    companion object {

        /*THis is to make new instance of this Track Fragment*/
        fun newInstance() = PlaylistFragment()
    }

    // This is an instance of view model of Track Fragment
    private lateinit var viewModel: TrackViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Here wer are inflating views on a canvas
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (appData.musician){
            FAB.show()
        } else {
            FAB.hide()
        }
        getPlaylists()

        create_playlist_btn.setOnClickListener{

            showAlertAndGetPlaylist()
//            var navigator = findNavController()
//            assert(navigator!=null)
//            var bundle = Bundle()
//            navigator.navigate(R.id.nav_upload_playlist, bundle)
        }





        FAB.setOnClickListener{
            var navigator = findNavController()
            assert(navigator!=null)
            var bundle = Bundle()
            navigator.navigate(R.id.nav_upload, bundle)
        }


    }

    private fun showAlertAndGetPlaylist() {
        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
        val view = View.inflate(context, R.layout.playlist_layout, null)


        builder.setCancelable(true)
        builder.setView(view)

        val playlistText = view.findViewById<EditText>(R.id.playlist_edit_text)

        builder.setPositiveButton("Create Playlist"
        ) { dialog, which ->
            if(playlistText.text.isNullOrEmpty()){
                showToast("Please provide a playlist name")
                return@setPositiveButton
            }

            postPlaylist(playlistText.text.toString())
            dialog.dismiss()
        }

        builder.create()
        builder.show()
    }

    fun postPlaylist(pname : String){
        var PlaylistService: PlaylistService? = null
        PlaylistService = ServiceBuilder.buildPlaylistService()
        var postPlaylist: Call<GenericResponse> = PlaylistService.postPlaylist(CreatePlaylist(name=pname,username = appData.username))!!
        postPlaylist.enqueue(object : Callback<GenericResponse> {
            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("app:", "Error Occurred : ${t.message}")
            }

            override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
            ) {

                // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                if (response.isSuccessful || response.body() != null) {
                    var responsebody: GenericResponse = response.body()!!
                    Log.e(
                            "CreatePlaylist",
                            "Response Body : $responsebody"
                    )
                }
            }

        })
        showToast("Playlist has been created!")
    }

    fun getPlaylists(): MutableList<AllPlaylistResponseItem>
    {
        var listOfPlaylist = mutableListOf<AllPlaylistResponseItem>()
        var PlaylistService: PlaylistService? = null
        PlaylistService = ServiceBuilder.buildPlaylistService()
        var getPlaylist: Call<AllPlaylistResponse> = PlaylistService.getPlaylist(userName(username = appData.username))!!
        getPlaylist.enqueue(object : Callback<AllPlaylistResponse> {
            override fun onFailure(call: Call<AllPlaylistResponse>, t: Throwable) {
                Log.e("app:", "Error Occurred : ${t.message}")
            }

            override fun onResponse(
                call: Call<AllPlaylistResponse>,
                response: Response<AllPlaylistResponse>
            ) {

                // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                if (response.isSuccessful || response.body() != null) {
                    var responsebody: AllPlaylistResponse = response.body()!!
                    Log.e(
                        "app:Track Info Response",
                        "Response Body : $responsebody"
                    )
                    //Should get all Instruments from here

                    for (i in responsebody) {
                        listOfPlaylist.add(i)
                    }

                    playAdapter = PlaylistAdapter(listOfPlaylist)

                    // This is recyclerview. First we are initiating a layout orientation
                    playlists.layoutManager = LinearLayoutManager(context)

                    //Then we are attaching a custom adapter to it.
                    playlists.adapter = playAdapter

                    playAdapter!!.setOnItemChildClickListener { adapter, view, position ->
                        when (view.id) {
                            R.id.playlist_name -> {
                                showToast("Playlist Name Clicked")
                            }

                            R.id.playlist_cons -> {
                              showToast("Playlist Clicked")
                              var navigator = findNavController()
                              assert(navigator!=null)
                              var bundle = Bundle()
                              listOfPlaylist[position].track_list
                              bundle.putString("data", Gson().toJson(listOfPlaylist[position],AllPlaylistResponseItem::class.java))
                              navigator.navigate(R.id.nav_playlist_tracks, bundle)

                            }
                        }
                    }
                }
            }

        })
        return listOfPlaylist

    }
}



