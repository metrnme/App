package com.example.mtrnme2.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mtrnme2.R
import com.example.mtrnme2.models.AllUserResponseItem
import com.example.mtrnme2.models.User

class UsersAdapter(data : MutableList<AllUserResponseItem>) : BaseQuickAdapter<AllUserResponseItem, BaseViewHolder>(R.layout.single_user_layout, data) {
    override fun convert(helper: BaseViewHolder?, item: AllUserResponseItem?) {
        helper!!.setText(R.id.name_tv, item!!.name.toString())
        helper.setText(R.id.uname_tv, item.username.toString())
        helper.addOnClickListener(R.id.dots)
    }
//What information are we displaying when we search for user on screen set it here like track.
    //like, we want name and username for now, that'll suffice.
    //but, we can check how it displays list of followers wagheraa. Can we count the number of followers from the array?
    //Also, I think followers thay kuch lougon ke in the db
    //well, shit.
    //abhi ke liye name or username hi display kar laite hain.

}