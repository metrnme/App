package com.example.mtrnme2.interfaces

import android.view.View
import com.example.mtrnme2.models.AllInstrumentResponseItem

open interface InstrumentInterface {
    public fun onCheckChanged(viewID : View, position : Int, isChecked : Boolean, data: MutableList<AllInstrumentResponseItem>)
}