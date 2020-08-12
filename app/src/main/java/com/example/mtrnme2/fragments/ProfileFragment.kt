package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amazonaws.util.ValidationUtils.assertNotNull
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.mtrnme2.R
import com.example.mtrnme2.adapters.TrackAdapter
import com.example.mtrnme2.databinding.FragmentProfileBinding
import com.example.mtrnme2.models.*
import com.example.mtrnme2.services.PlaylistService
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import com.example.mtrnme2.services.UserService
import com.example.mtrnme2.states.FallUnfallStates
import com.example.mtrnme2.states.LikeState
import com.google.gson.Gson
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt

class ProfileFragment : BaseFragment() {
    var trkAdapter: TrackAdapter? = null

    private lateinit var binding: FragmentProfileBinding
    var follow: FallUnfallStates = FallUnfallStates.NotFalling

    companion object {

        /*THis is to make new instance of this Profile Fragment*/
        fun newInstance() = ProfileFragment()
        private var globalMusicData: AllUserResponseItem? = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ProfileFragment.globalMusicData =
            Gson().fromJson(arguments?.getString("data"), AllUserResponseItem::class.java)

        binding.nameTxt.text = globalMusicData!!.name
        binding.unameTxt.text = globalMusicData!!.username
        var inst: String = ""
        for (i in globalMusicData!!.inst) {
            inst += "  $i"
        }
        binding.instTxt.text = inst
        binding.contactString.text = globalMusicData!!.bio

        Amplify.Storage.getUrl(globalMusicData!!.imgUrl,
            { result ->
                var imageurl = result.url.toString()
                Glide.with(this)
                        .load(imageurl) // image url
                        .placeholder(R.drawable.album_art_background) // any placeholder to load at start
                        .error(R.drawable.album_art_error)  // any image in case of error
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transform(CircleCrop())
                        .into(binding.imgProfile)
            },
            { error -> Log.e("error", error.message) })
        //Check if already following
//        var followState : Boolean = false
//        for( u in globalMusicData!!.followers){
//            if(u.toString().equals(appData.username)){
//                followState=true
//            }
//        }
        binding.followUser.isChecked = ProfileFragment.globalMusicData?.followers?.contains(appData.username)!!


        //Get User Following
        binding.followers.text= globalMusicData!!.followers.size.toString()
        binding.following.text= globalMusicData!!.following.size.toString()
        binding.followUser.setOnCheckedChangeListener { p0, p1 ->
            if(p1){
                //
                globalMusicData!!.following.contains(appData.username)
                following()
                follow = FallUnfallStates.Falling
            }
            else{
                //
                unfollow()
                follow = FallUnfallStates.NotFalling
            }
        }

        getAllUserTracks()

    }
    fun getAllUserTracks():MutableList<AllTrackResponseItem> {
        var listOfTracks:MutableList<AllTrackResponseItem>  = ArrayList()//mutableListOf<AllTrackResponseItem>()
        var TrackService: TrackService? = null
        TrackService = ServiceBuilder.buildTrackService()
        var getTracks: Call<AllTrackResponse> = TrackService?.getUserTracks(userName(username = globalMusicData!!.username))!!
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
                        "UserTracks",
                        "Response Body : $responsebody"
                    )
                    //Should get all Instruments from here

                    for(i in responsebody){
                        listOfTracks.add(i)
                    }
                    trkAdapter = TrackAdapter(response.body()!!)
                    uploaded_songs.layoutManager = LinearLayoutManager(context)
                    uploaded_songs.adapter = trkAdapter
                    trkAdapter!!.setOnItemChildClickListener { adapter, view, position ->
                        when(view.id){
                            R.id.more->{
                                //Add to playlist

                                var listOfPlaylist:MutableList<AllPlaylistResponseItem> = ArrayList()
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
                                                        addtoPlaylist(parseInt(item.key), listOfTracks[position].track_id)
                                                        showToast(items[playlistPosition].key)
                                                    }
                                                    .show()
                                        }
                                    }
                                })
                            }

                            R.id.track_cons->{
                                showToast("Clicked!")
                                var navigator = findNavController()
                                assertNotNull(navigator,"Play")
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

    private fun following(){
        var FollowService: UserService? = null
        FollowService = ServiceBuilder.buildservice()
        var Followers: Call<GenericResponse> = FollowService?.userFollow(followUser(to= globalMusicData!!.username,from = appData.username))
        Followers.enqueue(object : Callback<GenericResponse> {
            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("app:", "Error Occurred : ${t.message}")
            }

            override fun onResponse(
                call: Call<GenericResponse>,
                response: Response<GenericResponse>
            ) {
                var responsebody: GenericResponse = response.body()!!
                Log.e(
                    "Follow",
                    "Response Body : $responsebody"
                )
                showToast("Followed User")
            }
        })
    }

    private fun unfollow(){
        var UnfollowService: UserService? = null
        UnfollowService = ServiceBuilder.buildservice()
        var Unfollow: Call<GenericResponse> = UnfollowService?.userUnfollow(followUser(to= globalMusicData!!.username,from = appData.username))
        Unfollow.enqueue(object : Callback<GenericResponse> {
            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("app:", "Error Occurred : ${t.message}")
            }

            override fun onResponse(
                call: Call<GenericResponse>,
                response: Response<GenericResponse>
            ) {
                var responsebody: GenericResponse = response.body()!!
                Log.e(
                    "Follow",
                    "Response Body : $responsebody"
                )
                showToast("Unfollowed User")
            }
        })

    }
}