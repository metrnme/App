package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.mtrnme2.R
import com.example.mtrnme2.adapters.TrackAdapter
import com.example.mtrnme2.databinding.FragmentProfileBinding
import com.example.mtrnme2.models.AllTrackResponse
import com.example.mtrnme2.models.AllTrackResponseItem
import com.example.mtrnme2.models.AllUserResponseItem
import com.example.mtrnme2.models.userName
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : BaseFragment() {
    var trkAdapter: TrackAdapter? = null

    private lateinit var binding: FragmentProfileBinding

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
        var imageurl = ""
        Amplify.Storage.getUrl(globalMusicData!!.imgUrl,
            { result ->
                imageurl = result.url.toString()
            },
            { error -> Log.e("error", error.message) })
        Glide.with(this)
            .load(imageurl) // image url
            .placeholder(R.drawable.album_art_background) // any placeholder to load at start
            .error(R.drawable.album_art_error)  // any image in case of error
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .transform(CircleCrop())
            .into(binding.imgProfile)

        getAllUserTracks()
    }
    fun getAllUserTracks():MutableList<AllTrackResponseItem> {
        var listOfTracks  = mutableListOf<AllTrackResponseItem>()
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
                                showToast("More")
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
}