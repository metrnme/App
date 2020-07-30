package com.example.mtrnme2.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import com.example.mtrnme2.R
import com.example.mtrnme2.databinding.FragmentPlayerBinding
import com.example.mtrnme2.models.AllTrackResponseItem
import com.example.mtrnme2.states.PlayerState
import com.google.gson.Gson


class PlayerFragment : Fragment() {
    private lateinit var binding: FragmentPlayerBinding
    private var currentPlayerState: PlayerState = PlayerState.STARTED
    private var mediaPlayer: MediaPlayer? = null
    companion object {
        fun getNewInstance(): PlayerFragment {
            return PlayerFragment()
        }
        private var globalMusicData : AllTrackResponseItem?=null
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        globalMusicData = Gson().fromJson(arguments?.getString("data"), AllTrackResponseItem::class.java)

        binding.title.text = globalMusicData?.name.toString()
        var trackId = globalMusicData!!.track_id;
        var imgKey = globalMusicData!!.image_url;
        var imageurl=""
        Amplify.Storage.getUrl(imgKey,
            { result ->
                imageurl=result.url.toString()
            },
            { error -> Log.e("error",error.message) })
        var myGenre = globalMusicData!!.genre;
        var DisplayGenre="";
        for(i in myGenre){
            DisplayGenre=DisplayGenre+" "+i
        }
        var myInst = globalMusicData!!.inst_used;
        var DisplayInst="";
        for(i in myInst){
            DisplayInst=DisplayInst+" "+i
        }
        var track_url="";
        Amplify.Storage.getUrl(globalMusicData!!.url.toString(),
            { result -> track_url=result.url.toString() },
            { error -> Log.e("error",error.message.toString()) })

        binding.inst.text = DisplayInst
        binding.genre.text = DisplayGenre

        Glide.with(this)
            .load(imageurl) // image url
            .placeholder(R.drawable.album_art_background) // any placeholder to load at start
            .error(R.drawable.album_art_error)  // any image in case of error
            .override(350, 350) // resizing
            .into(binding.img);


        binding.playTrack.setOnCheckedChangeListener { p0, p1 ->
            binding.progressView.visibility = View.VISIBLE
            if (p1) {
                currentPlayerState = PlayerState.PLAYING
                mediaPlayer = MediaPlayer()

                if(track_url!="") {
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
            }else{
                Toast.makeText(requireContext(), "Media is null. Please provide", Toast.LENGTH_SHORT).show()
            }
        }
    }
}