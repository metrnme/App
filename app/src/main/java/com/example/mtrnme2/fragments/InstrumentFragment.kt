package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mtrnme2.R
import com.example.mtrnme2.activities.viewmodels.InstrumentViewModel
import com.example.mtrnme2.adapters.InstrumentAdapter
import com.example.mtrnme2.models.AllInstrumentResponse
import com.example.mtrnme2.models.AllInstrumentResponseItem
import com.example.mtrnme2.services.InstrumentService
import com.example.mtrnme2.services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_instrument.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*var username = arguments!!.getString("username")*/
    }

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

        getInstruments()

        // Here wer are initiating reference to adpater with data we have of tracks



    }



    fun getInstruments(): MutableList<AllInstrumentResponseItem> {
        var listOfInstruments  = mutableListOf<AllInstrumentResponseItem>()
        var InstrumentService: InstrumentService? = null
        InstrumentService = ServiceBuilder.buildInstrumentService()
        var getInstruments: Call<AllInstrumentResponse> = InstrumentService?.getInstruments()!!
        getInstruments.enqueue(object : Callback<AllInstrumentResponse> {
            override fun onFailure(call: Call<AllInstrumentResponse>, t: Throwable) {
                Log.e("app: Failed to load Instrument Data","Error Occurred : ${t.message}")
            }
            override fun onResponse(
                call: Call<AllInstrumentResponse>,
                response: Response<AllInstrumentResponse>
            ) {

                // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                if (response.isSuccessful || response.body() != null) {
                    var responsebody: AllInstrumentResponse = response.body()!!
                    Log.e(
                        "app:User Info Response",
                        "Response Body : $responsebody"
                    )
                    //Should get all Instruments from here
                    for(i in responsebody){
                        listOfInstruments.add(i)
                    }

                    instAdapter = InstrumentAdapter(listOfInstruments)

                    // This is recyclerview. First we are initiating a layout orientation
                    instruments.layoutManager = LinearLayoutManager(context)

                    //Then we are attaching a custom adapter to it.
                    instruments.adapter = instAdapter

                    instAdapter!!.setOnItemChildClickListener { adapter, view, position ->

                    }
                }
            }
        })

        return listOfInstruments
    }
}
