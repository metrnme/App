package com.example.mtrnme2.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mtrnme2.R
import com.example.mtrnme2.models.AllInstrumentResponseItem

class InstrumentAdapter(data: MutableList<AllInstrumentResponseItem>) : BaseQuickAdapter<AllInstrumentResponseItem, BaseViewHolder>(R.layout.inst_layout, data){

    override fun convert(helper: BaseViewHolder?, item: AllInstrumentResponseItem?) {
        helper!!.setText(R.id.iname, item!!.name)
        helper.setText(R.id.itype, item.i_type)
    }
}