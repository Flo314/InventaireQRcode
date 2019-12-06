package com.example.inventaireqrcode.gadgetUi.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.inventaireqrcode.Gadget
import com.example.inventaireqrcode.repository.Repo

// savoir quand on a rafraichit la liste
data class GadgetListViewState(
    val hasGadgetChange: Boolean,
    val gadgets: List<Gadget>
)

/**
 * Logique de la vue
 */
class GadgetListViewModel(private val repo: Repo) : ViewModel() {

    // MediatorLiveData permet de déclencher viewState à partir d'une autre source -> (Repo)
    private val viewState = MediatorLiveData<GadgetListViewState>()
    fun getViewState(): LiveData<GadgetListViewState> = viewState

    init {
        // appel dès que le repo est modifié
        viewState.addSource(repo.getAllGadgets(), { gadgets ->
            val oldState = viewState.value!!
            viewState.value = oldState.copy(
                hasGadgetChange = true,
                gadgets = gadgets
            )
        })

        // initial state quand quelqu'un s'abonne
        viewState.value =
            GadgetListViewState(
                hasGadgetChange = false,
                gadgets = emptyList()
            )
    }

    // ajout d'un gadget
    fun addGadget(gadget: Gadget) {
        repo.addGadget(gadget)
    }
}