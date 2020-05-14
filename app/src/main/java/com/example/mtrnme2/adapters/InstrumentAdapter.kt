package com.example.mtrnme2.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mtrnme2.R
import com.example.mtrnme2.models.Instrument

class InstrumentAdapter(data: MutableList<Instrument>) : BaseQuickAdapter<Instrument, BaseViewHolder>(R.layout.inst_layout, data){

    override fun convert(helper: BaseViewHolder?, item: Instrument?) {
        helper!!.setText(R.id.iname, item!!.i_name)
        helper.setText(R.id.itype, item.i_type)
    }
}