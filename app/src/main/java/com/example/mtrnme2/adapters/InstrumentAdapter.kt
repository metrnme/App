package com.example.mtrnme2.adapters

import android.widget.CompoundButton
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mtrnme2.R
import com.example.mtrnme2.interfaces.InstrumentInterface
import com.example.mtrnme2.models.AllInstrumentResponseItem

class InstrumentAdapter(data: MutableList<AllInstrumentResponseItem>) :
    BaseQuickAdapter<AllInstrumentResponseItem, BaseViewHolder>(R.layout.inst_layout, data) {

    var bindedCalls: InstrumentInterface? = null

    override fun convert(helper: BaseViewHolder?, item: AllInstrumentResponseItem?) {
        helper!!.setText(R.id.iname, item!!.name)
        helper.setText(R.id.itype, item.i_type)
        helper.setOnCheckedChangeListener(R.id.i_check,
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if(bindedCalls!=null){
                    bindedCalls?.onCheckChanged(buttonView, helper.adapterPosition, isChecked, data)
                }
            })
    }

    public fun setCalls(call: InstrumentInterface) {
        this.bindedCalls = call
    }
}