package com.example.mtrnme2.activities.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mtrnme2.models.Instrument

class InstrumentViewModel : ViewModel() {
    fun getInstruments(): MutableList<Instrument> {
        var listOfInstruments = mutableListOf<Instrument>()
        listOfInstruments.add(Instrument("Bass Guitar", "STR"))
        listOfInstruments.add(Instrument("Drums", "PER"))
        listOfInstruments.add(Instrument("Electric Guitar", "STR"))
        listOfInstruments.add(Instrument("Violin", "STR"))
        listOfInstruments.add(Instrument("Cajón Drums", "PER"))


        return listOfInstruments
    }
}