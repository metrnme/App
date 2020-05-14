package com.example.mtrnme2.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtrnme2.R
import com.example.mtrnme2.activities.viewmodels.TrackViewModel
import com.example.mtrnme2.adapters.TrackAdapter
import com.example.mtrnme2.models.Track
import kotlinx.android.synthetic.main.track_fragment.*

class TrackFragment : Fragment() {

    var listOfTracks : MutableList<Track> = mutableListOf()
    var tracksAdapter : TrackAdapter ?=null

    companion object {
        fun newInstance() = TrackFragment()
    }

    private lateinit var viewModel: TrackViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.track_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TrackViewModel::class.java)

        listOfTracks.add(Track("1", "Fly me to the moon", "https://google.com", "johndoe", "23"))
        listOfTracks.add(Track("2", "Fly me to the moon 2", "https://google.com", "johndoe", "23"))
        listOfTracks.add(Track("3", "Fly me to the moon 3", "https://google.com", "johndoe", "23"))
        listOfTracks.add(Track("4", "Fly me to the moon 4", "https://google.com", "johndoe", "23"))
        listOfTracks.add(Track("5", "Fly me to the moon 5", "https://google.com", "johndoe", "23"))
        listOfTracks.add(Track("6", "Fly me to the moon 6", "https://google.com", "johndoe", "23"))

        tracksAdapter = TrackAdapter(listOfTracks)

        tracks.layoutManager = LinearLayoutManager(context)
        tracks.adapter = tracksAdapter
    }

}
