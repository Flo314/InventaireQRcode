package com.example.inventaireqrcode.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inventaireqrcode.Gadget
import java.util.*

class Repo {
    // stockage gadgets dans une liste
    private val gadgets = mutableListOf<Gadget>()

    // chaque fois qu'on ajoute un gadget à la liste on le fait remonter avec le liveData
    private val gadgetsLiveData = MutableLiveData<List<Gadget>>()
    // pour ne pas donnée accès à la modification de la liste
    fun getAllGadgets(): LiveData<List<Gadget>> = gadgetsLiveData

    companion object {
        private var gadgetId = 1
    }

    // ajout d'un gadget
    fun addGadget(gadget: Gadget) {
        gadget.id = gadgetId
        gadget.dateCreated = Date()
        gadgets.add(0, gadget)
        gadgetId++

        // remonter la liste
        gadgetsLiveData.value = gadgets
    }
}