package com.example.mtrnme2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter

import com.example.mtrnme2.R
import com.example.mtrnme2.activities.viewmodels.TrackViewModel
import com.example.mtrnme2.activities.viewmodels.UserViewModel
import com.example.mtrnme2.adapters.TrackAdapter
import com.example.mtrnme2.adapters.UsersAdapter
import kotlinx.android.synthetic.main.track_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    var usersAdapter : UsersAdapter?=null

    companion object {

        /*THis is to make new instance of this Search Fragment*/
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ///Initiating instance of viewmodel
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        // Here wer are initiating reference to adpater with data we have of tracks
        usersAdapter = UsersAdapter(viewModel.getUsers())

        // This is recyclerview. First we are initiating a layout orientation
        tracks.layoutManager = LinearLayoutManager(context)

        //Then we are attaching a custom adapter to it.
        tracks.adapter = usersAdapter

        usersAdapter!!.setOnItemChildClickListener { adapter, view, position ->

        }
    }
}
