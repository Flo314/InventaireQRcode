package com.example.inventaireqrcode.gadgetUi.list


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
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
import com.example.inventaireqrcode.widgets.FabSmall
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

    private var isFabMenuOpen = false

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

        fabQRCode.setOnClickListener { navigateToQRCodeSan() }
        fabNfcRead.setOnClickListener { navigateToNfcodeSanRead() }

        fab.setOnClickListener { viewModel.toggleFabMenu() }
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

        if (state.isFabMenuOpen) {
            openFabMenu()
        } else {
            closeFabMenu()
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

    private fun openFabMenu() {
        ViewCompat.animate(fab)
            .rotation(45f)
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(10f))
            .start()

        animateShowFab(fabQRCode)
        animateShowFab(fabNfcRead)
    }

    private fun closeFabMenu() {
        ViewCompat.animate(fab)
            .rotation(0f)
            .setDuration(300)
            .setInterpolator(OvershootInterpolator(10f))
            .start()
        animateHideFab(fabQRCode)
        animateHideFab(fabNfcRead)
    }

    private fun animateShowFab(fab: FabSmall) {
        ViewCompat.animate(fab)
            .translationY(-fab.offsetYAnimation)
            .withStartAction { fab.visibility = View.VISIBLE }
            .withEndAction {
                fab.labelView.animate()
                    .alpha(1.0f)
                    .duration = 200
            }
    }

    private fun animateHideFab(fab: FabSmall) {
        ViewCompat.animate(fab)
            .translationY(0f)
            .withStartAction { fab.labelView.animate().alpha(0f) }
            .withEndAction { fab.visibility = View.GONE }
    }

}
