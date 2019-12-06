package com.example.inventaireqrcode.gadgetUi

import androidx.lifecycle.ViewModel
import com.example.inventaireqrcode.Gadget
import com.example.inventaireqrcode.repository.Repo

/**
 * Logique de la vue
 */
class GadgetListViewModel(private val repo: Repo) : ViewModel() {

    // ajout d'un gadget
    fun addGadget(gadget: Gadget) {
        repo.addGadget(gadget)
    }
}