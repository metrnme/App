//package com.example.mtrnme2.adapters
//
//import com.chad.library.adapter.base.BaseQuickAdapter
//import com.chad.library.adapter.base.BaseViewHolder
//import com.example.mtrnme2.R
//import com.example.mtrnme2.models.AllTrackResponseItem
//
//class PlaylistAdapter (data : MutableList<AllTrackResponseItem>) : BaseQuickAdapter<AllTrackResponseItem, BaseViewHolder>(
//    R.layout.single_track_layout, data) {
//    override fun convert(helper: BaseViewHolder?, item: AllTrackResponseItem?) {
//
//        /*Help contains  many mehtods like settext , set imageresource etc It will help you populate view from data*/
//        helper!!.setText(R.id.tname, item!!.name.toString())
//        helper!!.setText(R.id.likes, item!!.likes.toString())
//        helper!!.setText(R.id.username, item!!.username.toString())
//        helper!!.setText(R.id.url,item!!.url.toString())
//        helper.addOnClickListener(R.id.more, R.id.track_cons)
//    }
//}