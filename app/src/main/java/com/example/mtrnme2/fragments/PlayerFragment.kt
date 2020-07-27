package com.example.mtrnme2.fragments

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.Toast
import android.widget.ToggleButton
import com.example.mtrnme2.R
import com.example.mtrnme2.databinding.FragmentPlayerBinding
import com.example.mtrnme2.states.PlayerState

class PlayerFragment : Fragment() {
    private lateinit var binding: FragmentPlayerBinding
    private var currentPlayerState: PlayerState = PlayerState.STARTED
    private var mediaPlayer: MediaPlayer? = null

    companion object {
        fun getNewInstance(): PlayerFragment {
            return PlayerFragment()
        }
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


        binding.playTrack.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (p1) {
                    binding.progressView.visibility = View.VISIBLE
                    currentPlayerState = PlayerState.PLAYING
                    mediaPlayer = MediaPlayer.create(context!!, R.raw.sound)
                    //mediaPlayer?.setDataSource(Uri.parse())
                    //mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer?.setOnPreparedListener { p0 ->
                        p0?.start()
                        binding.progressView.visibility = View.GONE
                    }
                } else {
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