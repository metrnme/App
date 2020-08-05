package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import com.example.mtrnme2.R
import com.example.mtrnme2.databinding.FragmentPlayerBinding
import com.example.mtrnme2.databinding.FragmentProfileBinding
import com.example.mtrnme2.models.AllTrackResponseItem
import com.example.mtrnme2.models.AllUserResponseItem
import com.google.gson.Gson


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    companion object {

        /*THis is to make new instance of this Profile Fragment*/
        fun newInstance() = ProfileFragment()
        private var globalMusicData : AllUserResponseItem?=null
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
        ProfileFragment.globalMusicData = Gson().fromJson(arguments?.getString("data"), AllUserResponseItem::class.java)
        binding.unameView.text= globalMusicData!!.name
        binding.contactString.text = globalMusicData!!.bio
        var imageurl=""
        Amplify.Storage.getUrl(globalMusicData!!.imgUrl,
            { result ->
                imageurl=result.url.toString()
            },
            { error -> Log.e("error",error.message) })
        Glide.with(this)
            .load(imageurl) // image url
            .placeholder(R.drawable.album_art_background) // any placeholder to load at start
            .error(R.drawable.album_art_error)  // any image in case of error
            .override(350, 350) // resizing
            .into(binding.imgProfile);
    }
}