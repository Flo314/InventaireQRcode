package com.example.inventaireqrcode.qrcode


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.inventaireqrcode.App
import com.example.inventaireqrcode.BuildConfig
import com.example.inventaireqrcode.GadgetQRCode

import com.example.inventaireqrcode.R
import com.example.inventaireqrcode.gadgetUi.GadgetUiViewModelFactory
import com.example.inventaireqrcode.gadgetUi.list.GadgetListViewModel
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_qrcode_scan.*
import kotlinx.android.synthetic.main.item_gadget.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import timber.log.Timber


private const val PERMISSION_REQUEST_CAMERA = 1

/**
 * A simple [Fragment] subclass.
 */
class QRCodeScanFragment : Fragment(), ZXingScannerView.ResultHandler {

    private lateinit var viewModel: GadgetListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qrcode_scan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // s'bonner sur les event que barecode scanner va nous renvoyer
        qrcodeView.setResultHandler(this)

        val factory = GadgetUiViewModelFactory(App.repo)
        viewModel = ViewModelProviders.of(activity!!, factory).get(GadgetListViewModel::class.java)

        // simulateur pour l'émulateur
        /*if (BuildConfig.QRCODE_SIMULATOR_ENABLED) {
            notifyScan("https://qrcode.scan")
        }*/
    }

    override fun onResume() {
        super.onResume()
        qrcodeView.setResultHandler(this)
        // est qu'on a la permission
        if (!hasCameraPermission()) {
            requestCameraPermission()
        } else {
            startCamera()
        }
    }

    override fun onPause() {
        super.onPause()
        stopCamera()
    }

    private fun startCamera() {
        qrcodeView.startCamera()
    }

    private fun stopCamera() {
        qrcodeView.stopCamera()
    }

    // notifier d'un résultat d'un scan
    override fun handleResult(rawResult: Result) {
        notifyScan(rawResult.text)
    }

    private fun notifyScan(text: String) {
        Timber.i("QRCode $text")
        // ajout d'un gadget
        viewModel.addGadget(GadgetQRCode(url = text))
        findNavController().popBackStack()
    }

    private fun hasCameraPermission() =
        ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED


    private fun requestCameraPermission() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CAMERA -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera()
            } else {
                findNavController().popBackStack()
            }
        }
    }


}
