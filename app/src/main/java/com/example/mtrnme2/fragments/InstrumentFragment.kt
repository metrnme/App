package com.example.mtrnme2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mtrnme2.R
import com.example.mtrnme2.activities.viewmodels.InstrumentViewModel
import com.example.mtrnme2.adapters.InstrumentAdapter
import kotlinx.android.synthetic.main.fragment_instrument.*

/**
 * A simple [Fragment] subclass.
 */
class InstrumentFragment : Fragment() {

    var instAdapter : InstrumentAdapter?=null

    companion object {

        /*THis is to make new instance of this Search Fragment*/
        fun newInstance() = InstrumentFragment()
    }

    private lateinit var viewModel: InstrumentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instrument, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ///Initiating instance of viewmodel
        viewModel = ViewModelProvider(this).get(InstrumentViewModel::class.java)


        // Here wer are initiating reference to adpater with data we have of tracks
        instAdapter = InstrumentAdapter(viewModel.getInstruments())

        // This is recyclerview. First we are initiating a layout orientation
        instruments.layoutManager = LinearLayoutManager(context)

        //Then we are attaching a custom adapter to it.
        instruments.adapter = instAdapter

        instAdapter!!.setOnItemChildClickListener { adapter, view, position ->

        }
    }
}
