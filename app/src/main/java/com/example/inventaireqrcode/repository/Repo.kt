package com.example.inventaireqrcode.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inventaireqrcode.Gadget
import com.example.inventaireqrcode.GadgetNfc
import com.example.inventaireqrcode.GadgetQRCode
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

    // récup un gadgetQRCode par son id
    fun getGadgetQRCodeById(gadgetId: Int) : GadgetQRCode? {
        return findGadgetById(gadgetId)
    }

    // récup un gadgetNFC par son id
    fun getGadgetNfcById(gadgetId: Int) : GadgetNfc? {
        return findGadgetById(gadgetId)
    }

    // fonction qui accepte des types génériques
    @Suppress("UNCHECKED_CAST")
    private fun <T : Gadget> findGadgetById(gadgetId: Int) : T? {
        // find = qui prend un lambda en paramètre et qui demande un (predicat)
        // predicat = condition qui doit être respecté pour que find renvoi l'objet qui nous interesse
        // itère sur les gadgets du coup on récupère un gadget
        return gadgets.find { gadget -> gadget.id == gadgetId } as T
    }
}