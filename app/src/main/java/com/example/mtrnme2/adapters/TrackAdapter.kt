package com.example.mtrnme2.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mtrnme2.R
import com.example.mtrnme2.models.Track
/* This is a simple adapter class. It will take a list of data and convert it to single rows*/
class TrackAdapter (data : MutableList<Track>) : BaseQuickAdapter<Track, BaseViewHolder>(R.layout.single_track_layout, data) {
    override fun convert(helper: BaseViewHolder?, item: Track?) {

        /*Help contains  many mehtods like settext , set imageresource etc It will help you populate view from data*/
        helper!!.setText(R.id.title, item!!.name)
        helper!!.setText(R.id.duration, item!!.likes)
        helper!!.setText(R.id.description, item!!.username)

        helper.addOnClickListener(R.id.more)
    }
}