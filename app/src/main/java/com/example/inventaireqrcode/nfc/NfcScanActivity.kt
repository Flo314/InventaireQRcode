package com.example.inventaireqrcode.nfc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inventaireqrcode.R

class NfcScanActivity : AppCompatActivity() {

    companion object {
        const val NFC_MODE_READ = 0
        const val NFEC_MODE_WRITE = 1
    }

    private var nfcMode = NFC_MODE_READ

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_scan)
    }
}
