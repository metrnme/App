package com.example.mtrnme2.adapters

import android.util.Log
import com.amplifyframework.core.Amplify
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mtrnme2.R
import com.example.mtrnme2.models.AllUserResponseItem

class MainUserAdapter(data :  MutableList<AllUserResponseItem>) : BaseQuickAdapter<AllUserResponseItem, BaseViewHolder>(
    R.layout.single_user_home_layout, data) {
    override fun convert(helper: BaseViewHolder?, item: AllUserResponseItem?) {
        helper!!.setText(R.id.user_name, item!!.name.toString())

        Amplify.Storage.getUrl(
            item.imgUrl,
            { result ->
                Glide.with(mContext)
                    .load(result.url.toString())
                    .centerCrop()
                    .placeholder(R.drawable.album_art_error)
                    .into(helper?.getView(R.id.user_pic)!!)
            },
            { error -> Log.e("error", error.message) })
    }
}