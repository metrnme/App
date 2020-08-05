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


}