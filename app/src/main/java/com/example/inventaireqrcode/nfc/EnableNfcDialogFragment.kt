package com.example.inventaireqrcode.nfc

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.inventaireqrcode.R

class EnableNfcDialogFragment : DialogFragment() {

    interface EnableNfcDialogListener {
        fun onPositiveClick()
        fun onNegativeClick()
    }

    var listener: EnableNfcDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context!!)
            .setTitle("NFC Disabled")
            .setIcon(R.drawable.scan)
            .setMessage("Please enable NFC in the settings to scan an NFC tag")
            .setPositiveButton("Go to NFC settings") {_,_ -> listener?.onPositiveClick()}
            .setNegativeButton("Cancel") {_,_ -> listener?.onNegativeClick()}
            .create()
    }
}