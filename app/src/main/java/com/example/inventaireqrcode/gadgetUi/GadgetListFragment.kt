package com.example.inventaireqrcode.gadgetUi


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.inventaireqrcode.App

import com.example.inventaireqrcode.R

/**
 * A simple [Fragment] subclass.
 */
class GadgetListFragment : Fragment() {

    private lateinit var viewModel: GadgetListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gadget_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // exposer le viewModel sur le fragment
        val factory = GadgetUiViewModelFactory(App.repo)
        viewModel = ViewModelProviders.of(activity!!, factory).get(GadgetListViewModel::class.java)
        viewModel.getViewState().observe(this, Observer { updateUi(it!!) })
    }

    private fun updateUi(state: GadgetListViewState) {

    }


}
