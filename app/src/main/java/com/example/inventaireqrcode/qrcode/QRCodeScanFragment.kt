package com.example.inventaireqrcode.qrcode


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController

import com.example.inventaireqrcode.R


private const val PERMISSION_REQUEST_CAMERA = 1

/**
 * A simple [Fragment] subclass.
 */
class QRCodeScanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qrcode_scan, container, false)
    }

    override fun onResume() {
        super.onResume()
        // est qu'on a la permission
        if (!hasCameraPermission()) {
            requestCameraPermission()
        }
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

            } else {
                findNavController().popBackStack()
            }
        }
    }

}
