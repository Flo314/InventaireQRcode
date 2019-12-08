package com.example.inventaireqrcode.gadgetUi.detailqrcode

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.inventaireqrcode.R

class GadgetQRCodeDetailFragment : Fragment() {

    companion object {
        fun newInstance() = GadgetQRCodeDetailFragment()
    }

    private lateinit var viewModel: GadgetQRCodeDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gadget_qrcode_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GadgetQRCodeDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
