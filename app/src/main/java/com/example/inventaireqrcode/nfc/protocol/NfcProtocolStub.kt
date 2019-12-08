package com.example.inventaireqrcode.nfc.protocol

import android.content.Intent
import timber.log.Timber

/**
 * Ecriture d'un comportement par defaut de l'interface NfProtocol
 */
class NfcProtocolStub(override val listener: NfcProtocol.NfcReaderListener?) : NfcProtocol{

    companion object {
        private var counter = 1
    }

    override fun lockNfc() {
        Timber.i("Locking NFC")
    }

    override fun unlockNfc() {
        Timber.i("Unlocking NFC")
    }

    override fun readNfcTag(intent: Intent) {
        Timber.i("Reading NFC TAG")
        listener?.onNfcDataReady("https://inventaire/nfc/$counter")
        counter++
    }

    override fun writeNfcTag(intent: Intent, data: String) {
        Timber.i("Write $data in NFC TAG")
    }
}