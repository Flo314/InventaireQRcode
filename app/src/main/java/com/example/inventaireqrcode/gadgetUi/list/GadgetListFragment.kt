package com.example.inventaireqrcode.gadgetUi.list


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventaireqrcode.*

import com.example.inventaireqrcode.gadgetUi.GadgetUiViewModelFactory
import com.example.inventaireqrcode.gadgetUi.detailqrcode.GadgetQRCodeDetailFragment
import com.example.inventaireqrcode.gadgetUi.detailqrcode.GadgetQRCodeDetailFragmentArgs
import com.example.inventaireqrcode.nfc.NfcScanActivity
import com.example.inventaireqrcode.widgets.getTransitionNameCompat
import kotlinx.android.synthetic.main.fragment_gadget_list.*
import kotlinx.android.synthetic.main.item_gadget.view.*
import timber.log.Timber

private const val REQUEST_SCAN_NFC_READ = 1

/**
 * A simple [Fragment] subclass.
 */
class GadgetListFragment : Fragment(), GadgetListAdapter.GadgetListAdapterListener {

    private lateinit var viewModel: GadgetListViewModel

    private lateinit var gadgetListAdapter: GadgetListAdapter
    private val gadgets = mutableListOf<Gadget>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gadget_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        gadgetListAdapter = GadgetListAdapter(context!!, gadgets, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = gadgetListAdapter

        // exposer le viewModel sur le fragment
        val factory =
            GadgetUiViewModelFactory(App.repo)
        viewModel = ViewModelProviders.of(activity!!, factory).get(GadgetListViewModel::class.java)
        viewModel.getViewState().observe(this, Observer { updateUi(it!!) })

        fab.setOnClickListener { navigateToQRCodeSan() }
    }

    // mise à jour quand on reçoit un état du viewstate
    private fun updateUi(state: GadgetListViewState) {
        Timber.i("New state=$state")
        // on met à jour l'adapter que si on a reçu des nouveaux gadgets
        if (state.hasGadgetChange){
            gadgets.clear()
            gadgets.addAll(state.gadgets)
            gadgetListAdapter.notifyDataSetChanged()
        }
    }

    private fun navigateToQRCodeSan() {
        val action = GadgetListFragmentDirections.actionGadgetListFragmentToQRCodeScanFragment()
        findNavController().navigate(action)
    }

    private fun navigateToNfcodeSanRead() {
        val intent = Intent(context, NfcScanActivity::class.java)
        startActivityForResult(intent, REQUEST_SCAN_NFC_READ)
    }

    override fun onGadgetClicked(gadget: Gadget,itemView: View) {
        // récupérer les 3 éléments étiquetés
        val iconView = itemView.findViewById<ImageView>(R.id.iconImageView)
        val urlTextView = itemView.findViewById<TextView>(R.id.urlTextView)
        val dateTextView = itemView.findViewById<TextView>(R.id.dateCreatedTextView)

        // Fragment back stack bug transition NOT Navigation component

        // quel est le type du gadget qu'on ai entrain de selectionner
        val fragment = when(gadget) {
            is GadgetQRCode -> {
                GadgetQRCodeDetailFragment().apply {
                    arguments = GadgetQRCodeDetailFragmentArgs(gadgetId = gadget.id)
                        .toBundle()
                }
                /*val extras = FragmentNavigatorExtras(
                    iconView to iconView.getTransitionNameCompat(),
                    urlTextView to urlTextView.getTransitionNameCompat(),
                    dateTextView to dateTextView.getTransitionNameCompat()
                )
                val action = GadgetListFragmentDirections.actionGadgetListFragmentToGadgetQRCodeDetailFragment(gadget.id)
                findNavController().navigate(action, extras)*/
            }

            is GadgetNfc -> {
                GadgetQRCodeDetailFragment()
            }
        }

        fragmentManager!!
            .beginTransaction()
            .addSharedElement(iconView, iconView.getTransitionNameCompat())
            .addSharedElement(urlTextView, urlTextView.getTransitionNameCompat())
            .addSharedElement(dateTextView, dateTextView.getTransitionNameCompat())
            .addToBackStack("GadgetDetail")
            .replace(R.id.navHostFragment, fragment)
            .commit()
    }

}
