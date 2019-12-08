package com.example.inventaireqrcode.nfc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.inventaireqrcode.R
import kotlinx.android.synthetic.main.activity_nfc_scan.*

class NfcScanActivity : AppCompatActivity() {

    companion object {
        const val NFC_MODE_READ = 0
        const val NFEC_MODE_WRITE = 1
    }

    private var nfcMode = NFC_MODE_READ

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_scan)

        // animation sur l'image du tag nfc
        nfcImageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink))
    }
}
