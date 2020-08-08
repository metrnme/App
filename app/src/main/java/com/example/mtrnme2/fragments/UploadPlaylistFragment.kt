package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtrnme2.R
import com.example.mtrnme2.adapters.PlaylistAdapter
import com.example.mtrnme2.databinding.FragmentUploadBinding
import com.example.mtrnme2.databinding.FragmentUploadPlaylistBinding
import com.example.mtrnme2.models.*
import com.example.mtrnme2.services.PlaylistService
import com.example.mtrnme2.services.ServiceBuilder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_user_registration.*
import kotlinx.android.synthetic.main.fragment_playlist.*
import kotlinx.android.synthetic.main.fragment_upload_playlist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UploadPlaylistFragment : BaseFragment() {
    private lateinit var binding: FragmentUploadPlaylistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentUploadPlaylistBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        fun newInstance() = UploadPlaylistFragment()
    }
    private fun checkValidations(): Boolean {
        if (playlistname_txt.text.isNullOrEmpty()) {
            return false
        }
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnAddplaylist.setOnClickListener {
            if(checkValidations()){
                var PlaylistService: PlaylistService? = null
                PlaylistService = ServiceBuilder.buildPlaylistService()
                var postPlaylist: Call<GenericResponse> = PlaylistService.postPlaylist(CreatePlaylist(name=binding.playlistnameTxt.text.toString(),username = appData.username))!!
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
            }else{
                showToast("Please enter a playlist name")
            }
        }

    }

}

