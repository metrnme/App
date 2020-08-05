package com.example.mtrnme2.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.example.mtrnme2.R
import com.example.mtrnme2.databinding.FragmentPlayerBinding
import com.example.mtrnme2.models.AllTrackResponseItem
import com.example.mtrnme2.models.TrackComments
import com.example.mtrnme2.models.trackID
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import com.example.mtrnme2.states.PlayerState
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlayerFragment : BaseFragment() {
    private lateinit var binding: FragmentPlayerBinding
    private var currentPlayerState: PlayerState = PlayerState.STARTED
    private var mediaPlayer: MediaPlayer? = null
    var imageurl = ""

    companion object {
        fun getNewInstance(): PlayerFragment {
            return PlayerFragment()
        }

        private var globalMusicData: AllTrackResponseItem? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayerBinding.inflate(inflater)
        binding.comments.isSelected = true
        return binding.root
    }

    fun getAllComments(id: Int): String {
        //   var listOfComments  = ArrayList<TrackCommentsItem>()
        var allComments: String = " "
        var TrackService: TrackService? = null
        TrackService = ServiceBuilder.buildTrackService()
        var getTrackComment: Call<TrackComments> =
            TrackService?.getTrackComments(trackID(track_id = id))!!
        getTrackComment.enqueue(
            object : Callback<TrackComments> {
                override fun onFailure(call: Call<TrackComments>, t: Throwable) {
                    Log.e("app:", "Error Occurred : ${t.message}")
                }

                override fun onResponse(
                    call: Call<TrackComments>,
                    response: Response<TrackComments>
                ) {

                    if (response.isSuccessful || response.body() != null) {
                        var responsebody: TrackComments = response.body()!!
                        Log.d(
                            "Comment",
                            "Response Body : $responsebody"
                        )
                        //Should get all Comments
                        var commentString = ""
                        for (i in responsebody) {
                            var x: String = i.content
                            var y: String = i.username
                            commentString += "- ${x}"
                            commentString += "@ ${y}"
                        }

                        if(commentString.isNullOrEmpty()){
                            binding.comments.text = "No Comments Available"
                        }else{
                            binding.comments.text = commentString
                            binding.comments.isSelected = true
                        }
                    }
                }
            }
        )
        return allComments
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        globalMusicData =
            Gson().fromJson(arguments?.getString("data"), AllTrackResponseItem::class.java)

        binding.title.text = globalMusicData?.name.toString()
        binding.tUsername.text = globalMusicData?.username.toString()
        var trackId = globalMusicData!!.track_id;
        var imgKey = globalMusicData!!.image_url;

        var allComments: String = getAllComments(trackId) //ArrayList<TrackCommentsItem>

        Amplify.Storage.getUrl(imgKey,
            { result ->
                imageurl = result.url.toString()
                showLog(imageurl)

                imageurl = "https://lh3.googleusercontent.com/6UgEjh8Xuts4nwdWzTnWH8QtLuHqRMUB7dp24JYVE2xcYzq4HA8hFfcAbU-R-PC_9uA1=w288-h288-n-rw"
                /*Glide.with(this)
                    .load(imageurl) // image url
                    .placeholder(R.drawable.album_art_background) // any placeholder to load at start
                    .error(R.drawable.album_art_error)  // any image in case of error
                    .override(350, 350) // resizing
                    .into(binding.img);*/


            },
            { error -> Log.e("error", error.message) })
        var myGenre = globalMusicData!!.genre;
        var DisplayGenre = "";
        for (i in myGenre) {
            DisplayGenre = DisplayGenre + " " + i
        }


        Glide.with(binding.img.context)
                .load(imageurl) // image url
                .error(R.drawable.album_art_error)
                .centerCrop()
                .placeholder(R.drawable.album_art_background) // any placeholder to load at start / any image in case of error esizing
                .into(binding.img)
        var myInst = globalMusicData!!.inst_used;
        var DisplayInst = "";
        for (i in myInst) {
            DisplayInst = DisplayInst + " " + i
        }
        var track_url = "";
        Amplify.Storage.getUrl(globalMusicData!!.url.toString(),
            { result -> track_url = result.url.toString() },
            { error -> Log.e("error", error.message.toString()) })

        binding.inst.text = DisplayInst
        binding.genre.text = DisplayGenre

        Log.d("Comments", allComments)
        binding.comments.text = allComments

        binding.playTrack.setOnCheckedChangeListener { p0, p1 ->
            binding.progressView.visibility = View.VISIBLE
            if (p1) {
                currentPlayerState = PlayerState.PLAYING
                mediaPlayer = MediaPlayer()

                if (track_url != "") {
                    mediaPlayer?.setDataSource(track_url)
                    mediaPlayer?.prepareAsync()
                    //mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer?.setOnPreparedListener { p0 ->
                        p0?.start()
                        binding.progressView.visibility = View.GONE
                    }
                }
            } else {
                binding.progressView.visibility = View.GONE
                currentPlayerState = PlayerState.PAUSED
                mediaPlayer?.pause()
            }
        }

        binding.stopTrack.setOnClickListener {
            currentPlayerState = PlayerState.STOPPED
            if (mediaPlayer != null) {
                mediaPlayer?.stop()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Media is null. Please provide",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}