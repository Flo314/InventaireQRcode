package com.example.inventaireqrcode.nfc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.inventaireqrcode.R
import com.example.inventaireqrcode.nfc.protocol.DisabledNfcException
import com.example.inventaireqrcode.nfc.protocol.NfcProtocol
import com.example.inventaireqrcode.nfc.protocol.NfcProtocolIntent
import com.example.inventaireqrcode.nfc.protocol.NfcProtocolStub
import kotlinx.android.synthetic.main.activity_nfc_scan.*
import timber.log.Timber

class NfcScanActivity : AppCompatActivity(), NfcProtocol.NfcReaderListener {

    companion object {
        const val NFC_MODE_READ = 0
        const val NFEC_MODE_WRITE = 1
    }

    private var nfcMode = NFC_MODE_READ
    private lateinit var nfcProtocol: NfcProtocol

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_scan)

        //nfcProtocol = NfcProtocolStub(this)
        nfcProtocol = NfcProtocolIntent(this, this)
        lifecycle.addObserver(nfcProtocol)

        // animation sur l'image du tag nfc
        nfcImageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink))
    }

    override fun onNfcError(error: Throwable) {
        when (error) {
            is DisabledNfcException -> Timber.e(error, "Disabled NFC ERROR")
            else -> Timber.e(error, "Something went wrong with the NFC")
        }
    }

    override fun onNfcDataReady(data: String) {

    }
}
