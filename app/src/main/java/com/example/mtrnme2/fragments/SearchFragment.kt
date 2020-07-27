package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter

import com.example.mtrnme2.R
import com.example.mtrnme2.activities.viewmodels.UserViewModel
import com.example.mtrnme2.adapters.UsersAdapter
import com.example.mtrnme2.models.AllUserResponse
import com.example.mtrnme2.models.AllUserResponseItem
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.UserService
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    var usersAdapter: UsersAdapter? = null

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
        //  viewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        // Here wer are initiating reference to adpater with data we have of tracks
        //  usersAdapter = UsersAdapter(viewModel.getUsers())

        // This is recyclerview. First we are initiating a layout orientation
        // tracks.layoutManager = LinearLayoutManager(context)

        //Then we are attaching a custom adapter to it.
        //tracks.adapter = usersAdapter
        //yeh kyaa chuss hai?

        // usersAdapter!!.setOnItemChildClickListener { adapter, view, position ->
        var x=R.id.srchbar.toString()
        Log.d("app:Search",x )
        getUsers()
    }

    fun getUsers(): MutableList<AllUserResponseItem> {
        var listOfUsers = mutableListOf<AllUserResponseItem>()
        var UserService: UserService? = null
        UserService = ServiceBuilder.buildservice()
        var getUsers: Call<AllUserResponse> = UserService?.getAllUsers()!!
        getUsers.enqueue(object : Callback<AllUserResponse> {
            override fun onFailure(call: Call<AllUserResponse>, t: Throwable) {
                Log.e("app: Failed to load Track Data", "Error Occurred : ${t.message}")
            }

            override fun onResponse(
                call: Call<AllUserResponse>,
                response: Response<AllUserResponse>
            ) {

                // Log.e("app:Network Response", "Response Body : " + response.errorBody())
                if (response.isSuccessful || response.body() != null) {
                    var responsebody: AllUserResponse = response.body()!!
                    Log.e(
                        "app:User Info Response",
                        "Response Body : $responsebody"
                    )
                    //Should get all Instruments from here

                    for (i in responsebody) {
                        listOfUsers.add(i)
                    }

                    usersAdapter = UsersAdapter(response.body()!!)

                    // This is recyclerview. First we are initiating a layout orientation
                    users.layoutManager = LinearLayoutManager(context)

                    //Then we are attaching a custom adapter to it.
                    users.adapter = usersAdapter

                    usersAdapter!!.setOnItemChildClickListener { adapter, view, position ->
                    //yeh tracks kahaan se aarahay thay?
                    }
                }
            }
        })//YAYAYAY AB SAMAJH AAJAAYE Geee
        //This is too much work for today.
        //Tenkssss!
        //FollowApi check karnay lagaa hun

        return listOfUsers
    }

}
