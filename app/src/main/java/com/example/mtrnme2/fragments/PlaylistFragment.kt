package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtrnme2.R
import com.example.mtrnme2.activities.viewmodels.TrackViewModel
import com.example.mtrnme2.adapters.PlaylistAdapter
import com.example.mtrnme2.models.*
import com.example.mtrnme2.services.PlaylistService
import com.example.mtrnme2.services.ServiceBuilder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_playlist.*
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
        getPlaylists()
    }
    fun getPlaylistTracks(playlisttracks:List<Int>):MutableList<AllTrackResponseItem>{
        var listOfPlaylistTracks = mutableListOf<AllTrackResponseItem>()
        var PlaylistService: PlaylistService? = null
        PlaylistService = ServiceBuilder.buildPlaylistService()
        var getTrackPlaylist: Call<AllTrackResponse> = PlaylistService.getPlaylistTracks(getPlaylistTracks = playlisttracks)
        getTrackPlaylist.enqueue(object : Callback<AllTrackResponse> {
            override fun onFailure(call: Call<AllTrackResponse>, t: Throwable) {
                Log.e("app:", "Error Occurred : ${t.message}")
            }
            override fun onResponse(
                call: Call<AllTrackResponse>,
                response: Response<AllTrackResponse>
            ) {
                if (response.isSuccessful || response.body() != null) {
                    var responsebody: AllTrackResponse = response.body()!!
                    Log.e(
                        "app:Track Info Response",
                        "Response Body : $responsebody"
                    )
                    //Should get all Instruments from here
                    for (i in responsebody) {
                        listOfPlaylistTracks.add(i)
                    }
                }
            }

        })
        return listOfPlaylistTracks
    }

    fun getPlaylists(): MutableList<AllPlaylistResponseItem> {
        var listOfPlaylist = mutableListOf<AllPlaylistResponseItem>()
        var PlaylistService: PlaylistService? = null
        PlaylistService = ServiceBuilder.buildPlaylistService()
        var getPlaylist: Call<AllPlaylistResponse> = PlaylistService.getPlaylist(userName(username = "aceasad"))!!
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
//                                var playlist_tracks=listOfPlaylist[position].track_list
//                                var listOfPlaylistTracks=getPlaylistTracks(playlist_tracks)
//                                var navigator = findNavController()
//                                assert(navigator!=null)
//                                var bundle = Bundle()
//                                bundle.putString("data", Gson().toJson(listOfPlaylistTracks, AllTrackResponseItem::class.java))
//                                navigator.navigate(R.id.nav_track, bundle)
                            }
                        }
                    }
                }
            }

        })
        return listOfPlaylist

    }
}


