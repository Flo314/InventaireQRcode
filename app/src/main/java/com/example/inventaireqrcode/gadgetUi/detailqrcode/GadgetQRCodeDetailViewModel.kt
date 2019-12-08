package com.example.inventaireqrcode.gadgetUi.detailqrcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inventaireqrcode.GadgetQRCode
import com.example.inventaireqrcode.repository.Repo

class GadgetQRCodeDetailViewModel(private val repo: Repo) : ViewModel() {
    private val gadgetQRCodeLiveData = MutableLiveData<GadgetQRCode>()

    fun getGadgetQRCodeDetail(gadgetId: Int) : LiveData<GadgetQRCode> {
        gadgetQRCodeLiveData.value = repo.getGadgetQRCodeById(gadgetId)!!
        return gadgetQRCodeLiveData
    }
}
