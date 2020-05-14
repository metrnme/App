package com.example.mtrnme2.fragments

import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mtrnme2.R
import com.example.mtrnme2.activities.viewmodels.TrackViewModel
import com.example.mtrnme2.adapters.TrackAdapter
import com.example.mtrnme2.models.AllTrackResponse
import com.example.mtrnme2.models.Track
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.TrackService
import kotlinx.android.synthetic.main.track_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*This is Track Fragment. You will do all operations here like handling data , deleting data but through view model to make fragment clean*/
class TrackFragment : Fragment() {

    //This is an instance of Track Adapter
    var tracksAdapter : TrackAdapter ?=null

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
        return inflater.inflate(R.layout.track_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ///Initiating instance of viewmodel
        viewModel = ViewModelProvider(this).get(TrackViewModel::class.java)
      //  tracksAdapter = TrackAdapter(TrackViewModel.getTracks())

        tracksAdapter!!.setOnItemChildClickListener(object : BaseQuickAdapter.OnItemChildClickListener{
            override fun onItemChildClick(
                adapter: BaseQuickAdapter<*, *>?,
                view: View?,
                position: Int
            ) {
                /* This is the click listener. You can play files based on this*/
                playTrack(viewModel.getTracks()[position].url)
            }

        })
      //  getTracks()
        // Here wer are initiating reference to adpater with data we have of tracks

    }

    private fun getTracks() {
        var TrackService: TrackService? = null
        TrackService = ServiceBuilder.buildTrackService()
        var getTracks: Call<AllTrackResponse> = TrackService?.getAllTracks()!!
        getTracks.enqueue(object : Callback<AllTrackResponse> {
            override fun onFailure(call: Call<AllTrackResponse>, t: Throwable) {
                Toast.makeText(
                    context!!,
                    "Error Occurred : ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<AllTrackResponse>,
                response: Response<AllTrackResponse>
            ) {
                // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                if (response.isSuccessful || response.body() != null) {
                    var responsebody: AllTrackResponse = response.body()!!
                    Log.e(
                        "app:User Info Response",
                        "Response Body : " + responsebody.toString()
                    )
                    //Should get all Instruments from here

                    tracksAdapter = TrackAdapter(response.body()!!)

                    // This is recyclerview. First we are initiating a layout orientation
                    tracks.layoutManager = LinearLayoutManager(context)

                    //Then we are attaching a custom adapter to it.
                    tracks.adapter = tracksAdapter

                    tracksAdapter!!.setOnItemChildClickListener(object : BaseQuickAdapter.OnItemChildClickListener{
                        override fun onItemChildClick(
                            adapter: BaseQuickAdapter<*, *>?,
                            view: View?,
                            position: Int
                        ) {
                            /* This is the click listener. You can play files based on this*/
                            playTrack(viewModel.getTracks()[position].url)
                        }

                    })
                }
            }
        })
    }


    fun playTrack(url : String)
    {
        var media = MediaPlayer();
        media.setDataSource(context!!, Uri.parse(url))

        /*Got it? Yes got it Thank you so much!!!!! THis was awesume, i'll do the rest of it and all Great. DOnehogaya
        * */
    }


}
