package com.example.inventaireqrcode.gadgetUi.list


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventaireqrcode.*

import com.example.inventaireqrcode.gadgetUi.GadgetUiViewModelFactory
import kotlinx.android.synthetic.main.fragment_gadget_list.*
import timber.log.Timber

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

        // test d'ajout de gadget dans la liste
        viewModel.addGadget(GadgetQRCode(url = "https://qrcode"))
        viewModel.addGadget(GadgetNfc(url = "https://nfc"))
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

    override fun onGadgetClicked(gadget: Gadget) {

    }


}
