package com.example.mtrnme2.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mtrnme2.R
import com.example.mtrnme2.models.User

class UsersAdapter(data : MutableList<User>) : BaseQuickAdapter<User, BaseViewHolder>(R.layout.single_user_layout, data) {
    override fun convert(helper: BaseViewHolder?, item: User?) {
        helper!!.setText(R.id.name_tv, item!!.name)
        helper.setText(R.id.uname_tv, item.username)
        helper.addOnClickListener(R.id.dots)
    }


}