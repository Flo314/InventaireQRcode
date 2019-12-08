package com.example.inventaireqrcode.gadgetUi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventaireqrcode.gadgetUi.detailqrcode.GadgetQRCodeDetailViewModel
import com.example.inventaireqrcode.gadgetUi.list.GadgetListViewModel
import com.example.inventaireqrcode.repository.Repo
import java.lang.IllegalArgumentException

/**
 * Factory ViewModel générique
 * Gérer tous les cas de ViewModel qui sont lié aux GadgetUiViewModel
 */
@Suppress("UNCHECKED_CAST")
class GadgetUiViewModelFactory(private val repo: Repo) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(GadgetListViewModel::class.java) -> GadgetListViewModel(repo)
            modelClass.isAssignableFrom(GadgetQRCodeDetailViewModel::class.java) -> GadgetQRCodeDetailViewModel(repo)
            else -> throw IllegalArgumentException("Unexpected model class $modelClass")
        } as T
    }
}