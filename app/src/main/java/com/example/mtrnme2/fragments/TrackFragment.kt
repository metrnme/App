package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mtrnme2.R
import com.example.mtrnme2.activities.viewmodels.TrackViewModel
import com.example.mtrnme2.adapters.TrackAdapter
import com.example.mtrnme2.models.AllTrackResponse
import com.example.mtrnme2.models.AllTrackResponseItem
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import kotlinx.android.synthetic.main.track_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/*This is Track Fragment. You will do all operations here like handling data , deleting data but through view model to make fragment clean*/
class TrackFragment : Fragment() {

    //This is an instance of Track Adapter
    var trkAdapter : TrackAdapter ?=null

    companion object {

        /*THis is to make new instance of this Track Fragment*/
        fun newInstance() = TrackFragment()
    }

    // This is an instance of view model of Track Fragment
    private lateinit var viewModel: TrackViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Here wer are inflating views on a canvas
        return inflater.inflate(R.layout.track_fragment, container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(TrackViewModel::class.java)
     //   trkAdapter = TrackAdapter(viewModel.getSampleTracks())
     //   tracks.layoutManager = LinearLayoutManager(context)

        //Then we are attaching a custom adapter to it.
       // tracks.adapter = trkAdapter

        //trkAdapter!!.setOnItemChildClickListener { adapter, view, position ->

        //}

         getTracks()
        // Here wer are initiating reference to adpater with data we have of tracks

    }
    // Uses Retrofit Track Service and Populates the View to Return
    fun getTracks(): MutableList<AllTrackResponseItem> {
                var listOfTracks  = mutableListOf<AllTrackResponseItem>()
                var TrackService: TrackService? = null
                TrackService = ServiceBuilder.buildTrackService()
                var getTracks: Call<AllTrackResponse> = TrackService?.getAllTracks()!!
                getTracks.enqueue(object : Callback<AllTrackResponse> {
                    override fun onFailure(call: Call<AllTrackResponse>, t: Throwable) {
                        Log.e("app: Failed to load Track Data","Error Occurred : ${t.message}")
                    }
                    override fun onResponse(
                        call: Call<AllTrackResponse>,
                        response: Response<AllTrackResponse>
                    ) {

                        // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                        if (response.isSuccessful || response.body() != null) {
                            var responsebody: AllTrackResponse = response.body()!!
                            Log.e(
                                "app:Track Info Response",
                                "Response Body : $responsebody"
                            )
                            //Should get all Instruments from here

                            for(i in responsebody){
                                listOfTracks.add(i)
                            }

                            trkAdapter = TrackAdapter(response.body()!!)

                            // This is recyclerview. First we are initiating a layout orientation
                            tracks.layoutManager = LinearLayoutManager(context)

                            //Then we are attaching a custom adapter to it.
                            tracks.adapter = trkAdapter


                            trkAdapter!!.setOnItemChildClickListener { adapter, view, position ->
                                 Log.d("MTRNME-track"  , "onItemChildClick: ")

                             }
                        }
                    }
                })

                return listOfTracks
            }


    }



    //fun playTrack(url : String)
    //{
      //  var media = MediaPlayer();
       // media.setDataSource(context!!, Uri.parse(url))

        /*Got it? Yes got it Thank you so much!!!!! THis was awesume, i'll do the rest of it and all Great. DOnehogaya
        * */
    //}


