package com.example.inventaireqrcode.gadgetUi.detailqrcode

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.inventaireqrcode.App
import com.example.inventaireqrcode.GadgetQRCode

import com.example.inventaireqrcode.R
import com.example.inventaireqrcode.gadgetUi.GadgetUiViewModelFactory
import com.example.inventaireqrcode.widgets.toFormattedString
import kotlinx.android.synthetic.main.item_gadget.*

class GadgetQRCodeDetailFragment : Fragment() {

    private lateinit var viewModel: GadgetQRCodeDetailViewModel
    private var gadgetId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        gadgetId = GadgetQRCodeDetailFragmentArgs.fromBundle(arguments!!).gadgetId

        return inflater.inflate(R.layout.fragment_gadget_qrcode_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = GadgetUiViewModelFactory(App.repo)
        viewModel = ViewModelProviders.of(this, factory).get(GadgetQRCodeDetailViewModel::class.java)
        // s'abonner sur les event du viewmodel
        viewModel.getGadgetQRCodeDetail(gadgetId).observe(this, Observer { updateUi(it!!) })
    }

    private fun updateUi(gadgetQRCode: GadgetQRCode) {
        urlTextView.text = gadgetQRCode.url
        dateCreatedTextView.text = gadgetQRCode.dateCreated.toFormattedString()
    }

}
