package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtrnme2.R
import com.example.mtrnme2.adapters.TrackAdapter
import com.example.mtrnme2.databinding.FragmentPlaylistTrackBinding
import com.example.mtrnme2.models.AllPlaylistResponseItem
import com.example.mtrnme2.models.AllTrackResponse
import com.example.mtrnme2.models.AllTrackResponseItem
import com.example.mtrnme2.services.PlaylistService
import com.example.mtrnme2.services.ServiceBuilder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_playlist_track.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistTrackFragment : BaseFragment() {
    var trkAdapter : TrackAdapter?=null
    private lateinit var binding: FragmentPlaylistTrackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlaylistTrackBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        fun newInstance() = PlaylistTrackFragment()
        private var globalMusicData : AllPlaylistResponseItem?=null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        PlaylistTrackFragment.globalMusicData = Gson().fromJson(arguments?.getString("data"), AllPlaylistResponseItem::class.java)
        Log.d("PlaylistTracks",globalMusicData!!.track_list.toString())
        getPlaylistTracks(globalMusicData!!.track_list)

    }
    fun getPlaylistTracks(trackidlist : ArrayList<Int>): MutableList<AllTrackResponseItem>{
        var listOfTracks  = mutableListOf<AllTrackResponseItem>()
        var TrackService: PlaylistService? = null
        TrackService = ServiceBuilder.buildPlaylistService()
        var getTracks: Call<AllTrackResponse> = TrackService?.getPlaylistTracks(trackidlist)!!
        getTracks.enqueue(object : Callback<AllTrackResponse> {
            override fun onFailure(call: Call<AllTrackResponse>, t: Throwable) {
                Log.e("app:","Error Occurred : ${t.message}")
            }
            override fun onResponse(
                call: Call<AllTrackResponse>,
                response: Response<AllTrackResponse>
            ) {

                // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                if (response.isSuccessful || response.body() != null) {
                    var responsebody: AllTrackResponse = response.body()!!
                    Log.e(
                        "app:Track Info Response",
                        "Response Body : $responsebody"
                    )
                    //Should get all Instruments from here

                    for(i in responsebody){
                        listOfTracks.add(i)
                    }

                    trkAdapter = TrackAdapter(response.body()!!)
                    // This is recyclerview. First we are initiating a layout orientation
                    playlisttracks.layoutManager = LinearLayoutManager(context)

                    //Then we are attaching a custom adapter to it.
                    playlisttracks.adapter = trkAdapter

                    trkAdapter!!.setOnItemChildClickListener { adapter, view, position ->
                        when(view.id){
                            R.id.more->{
                                showToast("More")
                            }
                            R.id.track_cons->{
                                var navigator = findNavController()
                                assert(navigator!=null)
                                var bundle = Bundle()
                                bundle.putString("data", Gson().toJson(listOfTracks[position], AllTrackResponseItem::class.java))
                                navigator.navigate(R.id.nav_player, bundle)
                            }
                        }
                    }
                }
            }
        })

        return listOfTracks
    }
}
