package com.example.inventaireqrcode.gadgetUi.detailqrcode

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.inventaireqrcode.App
import com.example.inventaireqrcode.GadgetQRCode

import com.example.inventaireqrcode.R
import com.example.inventaireqrcode.gadgetUi.GadgetUiViewModelFactory
import com.example.inventaireqrcode.widgets.setTransitionNameCompat
import com.example.inventaireqrcode.widgets.toFormattedString
import kotlinx.android.synthetic.main.item_gadget.*

class GadgetQRCodeDetailFragment : Fragment() {

    private lateinit var viewModel: GadgetQRCodeDetailViewModel
    private var gadgetId: Int = -1

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_gadget_qrcode_detail, container, false)

        gadgetId = GadgetQRCodeDetailFragmentArgs.fromBundle(arguments!!).gadgetId

        // récupérer les 3 éléments étiquetés -> lien entre la listfragment et detailfragment
        view.findViewById<ImageView>(R.id.iconImageView).setTransitionNameCompat("icon", gadgetId)
        view.findViewById<TextView>(R.id.urlTextView).setTransitionNameCompat("url", gadgetId)
        view.findViewById<TextView>(R.id.dateCreatedTextView).setTransitionNameCompat("date", gadgetId)

        return view
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
