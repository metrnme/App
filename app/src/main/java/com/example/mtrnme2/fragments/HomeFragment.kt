package com.example.mtrnme2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mtrnme2.R
import com.example.mtrnme2.adapters.MainUserAdapter
import com.example.mtrnme2.adapters.UsersAdapter
import com.example.mtrnme2.models.AllUserResponse
import com.example.mtrnme2.models.AllUserResponseItem
import com.example.mtrnme2.services.ServiceBuilder
import com.example.mtrnme2.services.UserService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : BaseFragment() {

    var adapter: MainUserAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUsers()
    }

    fun getUsers(): MutableList<AllUserResponseItem> {
        var listOfUsers = mutableListOf<AllUserResponseItem>()
        var UserService: UserService? = null
        UserService = ServiceBuilder.buildservice()
        var getUsers: Call<AllUserResponse> = UserService?.getAllUsers()!!
        getUsers.enqueue(object : Callback<AllUserResponse> {
            override fun onFailure(call: Call<AllUserResponse>, t: Throwable) {
                Log.e("User Err", "Error Occurred : ${t.message}")
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

                    adapter = MainUserAdapter(responsebody)
                    users.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    users.adapter = adapter
                }
            }
        })

        return listOfUsers
    }
}



