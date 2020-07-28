package com.example.mtrnme2.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
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
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        globalMusicData = Gson().fromJson(arguments?.getString("data"), AllTrackResponseItem::class.java)

        binding.title.text = globalMusicData?.name.toString()

        binding.playTrack.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                binding.progressView.visibility = View.VISIBLE
                if (p1) {
                    currentPlayerState = PlayerState.PLAYING
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setDataSource(globalMusicData!!.url.toString())
                    mediaPlayer?.prepareAsync()
                    //mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer?.setOnPreparedListener { p0 ->
                        p0?.start()
                        binding.progressView.visibility = View.GONE
                    }
                } else {
                    binding.progressView.visibility = View.GONE
                    currentPlayerState = PlayerState.PAUSED
                    mediaPlayer?.pause()
                }
            }
        })

        binding.stopTrack.setOnClickListener {
            currentPlayerState = PlayerState.STOPPED
            if (mediaPlayer != null) {
                mediaPlayer?.stop()
            }else{
                Toast.makeText(context!!, "Media is null. Please provide", Toast.LENGTH_SHORT).show()
            }
        }
    }
}