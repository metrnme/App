package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtrnme2.R
import com.example.mtrnme2.activities.viewmodels.TrackViewModel
import com.example.mtrnme2.adapters.TrackAdapter
import com.example.mtrnme2.models.*
import com.example.mtrnme2.services.PlaylistService
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import com.google.gson.Gson
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import kotlinx.android.synthetic.main.track_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/*This is Track Fragment. You will do all operations here like handling data , deleting data but through view model to make fragment clean*/
class TrackFragment : BaseFragment() {

    //This is an instance of Track Adapter
    var trkAdapter : TrackAdapter ?=null

    companion object {

        /*THis is to make new instance of this Track Fragment*/
        fun newInstance() = TrackFragment()
 //       private var globalMusicData : AllTrackResponse?=null
    }

    // This is an instance of view model of Track Fragment
    private lateinit var viewModel: TrackViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Here wer are inflating views on a canvas
        return inflater.inflate(R.layout.track_fragment, container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getTracks()

    }
    // Uses Retrofit Track Service and Populates the View to Return
    fun getTracks(): MutableList<AllTrackResponseItem> {
                var listOfTracks  = mutableListOf<AllTrackResponseItem>()
                var TrackService: TrackService? = null
                TrackService = ServiceBuilder.buildTrackService()
                var getTracks: Call<AllTrackResponse> = TrackService?.getAllTracks()!!
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
                            tracks.layoutManager = LinearLayoutManager(context)

                            //Then we are attaching a custom adapter to it.
                            tracks.adapter = trkAdapter

                            trkAdapter!!.setOnItemChildClickListener { adapter, view, position ->
                                when(view.id){
                                    R.id.more->{
                                        //Add to playlist

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

                                                    val playlists = responsebody

                                                    showLog(playlists.size.toString() + " Size")

                                                    val items = ArrayList<SheetSelectionItem>()
                                                    for(list in playlists){
                                                        items.add(SheetSelectionItem(list.pl_id.toString(), list.name, R.drawable.ic_checkw))
                                                    }
                                                        SheetSelection.Builder(context!!)
                                                            .title("Choose Playlist")
                                                            .items(items)
                                                            .selectedPosition(2)
                                                            .showDraggedIndicator(true)
                                                            .searchEnabled(true)
                                                            .onItemClickListener { item, playlistPosition ->
                                                                addtoPlaylist(item.key.toInt(), listOfTracks[position].track_id)
                                                                showToast("Added to "+items[playlistPosition].value)
                                                            }
                                                            .show()
                                                }
                                            }
                                        })
                                    }

                                    R.id.track_cons->{
                                        var navigator = findNavController()
                                        assert(navigator!=null)
                                        var bundle = Bundle()
                                        bundle.putString("data", Gson().toJson(response.body()!![position], AllTrackResponseItem::class.java))
                                        navigator.navigate(R.id.nav_player, bundle)
                                    }
                                }
                            }
                        }
                    }
                })

                return listOfTracks
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
                }
            }
        })
        return listOfPlaylist

    }
    private fun addtoPlaylist(playlistId: Int,trackId: Int) {

    //Get all playlist here for a user and show in dialog for them to
    // select one of the playlist
    //then add the track to that playlist this thing is missing right now
    //we have to fixed the code after figuring out dialogs

        var PlayistService: PlaylistService? = null
        PlayistService = ServiceBuilder.buildPlaylistService()
        var addTracktoPlaylist: Call<GenericResponse> = PlayistService?.updatePlaylist(updatePlaylist(playlist_id = playlistId, track_id = trackId))!!
        addTracktoPlaylist.enqueue(object : Callback<GenericResponse> {
            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("app:", "Error Occurred : ${t.message}")
            }

            override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
            ) {
                var responsebody: GenericResponse = response.body()!!
                Log.e(
                        "Track2Playlist",
                        "Response Body : $responsebody"
                )
                showToast("Track has been added to playlist")
            }
        })

    }
}