package com.example.mtrnme2.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.mtrnme2.R
import com.example.mtrnme2.activities.DashboardActivity
import com.example.mtrnme2.adapters.TrackAdapter
import com.example.mtrnme2.databinding.FragmentProfileBinding
import com.example.mtrnme2.databinding.FragmentUserProfileBinding
import com.example.mtrnme2.models.*
import com.example.mtrnme2.services.PlaylistService
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import com.example.mtrnme2.services.UserService
import com.google.gson.Gson
import com.minibugdev.sheetselection.SheetSelection
import com.minibugdev.sheetselection.SheetSelectionItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.uname_txt
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_user_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Integer.parseInt

class UserProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentUserProfileBinding
    var userService : UserService?=null
    var trkAdapter: TrackAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userService = ServiceBuilder.buildservice()

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentUserProfileBinding.inflate(inflater)
        return binding.root
    }
    companion object {
        fun getNewInstance(): UserProfileFragment {
            return UserProfileFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var getUserCall : Call<AllUserResponseItem> = userService?.getUser(userName(username = appData.username))!!
        getUserCall.enqueue(object : Callback<AllUserResponseItem> {
            override fun onFailure(call: Call<AllUserResponseItem>, t: Throwable) {
            }

            override fun onResponse(
                    call: Call<AllUserResponseItem>,
                    response: Response<AllUserResponseItem>
            ) {
                Log.e("app Network Response", "Response Body : " + response.body())

                if (response.isSuccessful && response.body()!=null){
                    val responsebody : AllUserResponseItem = response.body()!!
                    Log.e("app New User Response", "Response Body : " + responsebody.username)
                    binding.nameTxt.text = responsebody!!.name
                    binding.unameTxt.text = responsebody!!.username
                    var inst: String = ""
                    for (i in responsebody!!.inst) {
                        inst += "  $i"
                    }
                    binding.instTxt.text = inst
                    binding.contactString.text = responsebody!!.bio
                    var imageurl:String=""
                    Amplify.Storage.getUrl(responsebody!!.imgUrl,
                            { result ->
                                imageurl = result.url.toString()
                                Glide.with(this@UserProfileFragment)
                                        .load(imageurl) // image url
                                        .placeholder(R.drawable.album_art_background) // any placeholder to load at start
                                        .error(R.drawable.album_art_error)  // any image in case of error
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .transform(CircleCrop())
                                        .into(binding.imgProfile)


                            },
                            { error -> Log.e("error", error.message) })


                }
            }

        })
        getAllUserTracks()
    }
    fun getAllUserTracks():MutableList<AllTrackResponseItem> {
        var listOfTracks  = mutableListOf<AllTrackResponseItem>()
        var TrackService: TrackService? = null
        TrackService = ServiceBuilder.buildTrackService()
        var getTracks: Call<AllTrackResponse> = TrackService?.getUserTracks(userName(username = appData.username))!!
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
                    user_uploaded_songs.layoutManager = LinearLayoutManager(context)
                    user_uploaded_songs.adapter = trkAdapter
                    trkAdapter!!.setOnItemChildClickListener { adapter, view, position ->
                        when(view.id){
                            R.id.more->{
                                //Delete Track
                                deleteTrack(responsebody!![position].track_id)
                            }

                            R.id.track_cons->{
                                showToast("Clicked!")
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

    private fun deleteTrack(trackID: Int) {
        var TrackService: TrackService? = null
        TrackService = ServiceBuilder.buildTrackService()
        var delete_Track: Call<GenericResponse> = TrackService?.deleteTrack(removeTrack(username = appData.username,track_id = trackID))!!
        delete_Track.enqueue(object : Callback<GenericResponse> {
            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                Log.e("app:", "Error Occurred : ${t.message}")
            }

            override fun onResponse(
                    call: Call<GenericResponse>,
                    response: Response<GenericResponse>
            ) {
                if (response.isSuccessful || response.body() != null) {
                    var responsebody: GenericResponse = response.body()!!
                    showToast("Track has been deleted!")
                }
            }

        })
    }
}