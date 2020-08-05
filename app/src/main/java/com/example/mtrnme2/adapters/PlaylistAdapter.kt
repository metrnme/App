package com.example.mtrnme2.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mtrnme2.R
import com.example.mtrnme2.models.AllPlaylistResponseItem

class PlaylistAdapter (data : MutableList<AllPlaylistResponseItem>) : BaseQuickAdapter<AllPlaylistResponseItem, BaseViewHolder>(
    R.layout.single_playlist_layout, data) {
    override fun convert(helper: BaseViewHolder?, item: AllPlaylistResponseItem?) {

        /*Help contains  many mehtods like settext , set imageresource etc It will help you populate view from data*/
        helper!!.setText(R.id.playlist_name, item!!.name.toString())
        helper!!.setText(R.id.username, item!!.username.toString())
        helper.addOnClickListener(R.id.playlist_name, R.id.playlist_cons)
    }
}