package com.example.mtrnme2.activities.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mtrnme2.models.Track

class TrackViewModel : ViewModel() {


    /*This function  will return a track list. You can get this list either from network or from offline database. It Returns a mutable list of track model*/
    fun getTracks() : MutableList<Track>{
        var listOfTracks = mutableListOf<Track>()
        listOfTracks.add(Track("1", "Fly me to the moon", "https://google.com", "johndoe", "23"))
        listOfTracks.add(Track("2", "Fly me to the moon 2", "https://google.com", "johndoe", "23"))
        listOfTracks.add(Track("3", "Fly me to the moon 3", "https://google.com", "johndoe", "23"))
        listOfTracks.add(Track("4", "Fly me to the moon 4", "https://google.com", "johndoe", "23"))
        listOfTracks.add(Track("5", "Fly me to the moon 5", "https://google.com", "johndoe", "23"))
        listOfTracks.add(Track("6", "Fly me to the moon 6", "https://google.com", "johndoe", "23"))

        return listOfTracks
    }
}
