package com.example.mtrnme2.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtrnme2.R
import com.example.mtrnme2.activities.viewmodels.TrackViewModel
import com.example.mtrnme2.adapters.TrackAdapter
import com.example.mtrnme2.models.Track
import kotlinx.android.synthetic.main.track_fragment.*

class TrackFragment : Fragment() {
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
        viewModel = ViewModelProvider(this).get(TrackViewModel::class.java)


        tracksAdapter = TrackAdapter(viewModel.getTracks())

        tracks.layoutManager = LinearLayoutManager(context)
        tracks.adapter = tracksAdapter
    }

}
