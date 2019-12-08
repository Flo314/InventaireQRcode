package com.example.inventaireqrcode.nfc.protocol

import android.content.Intent

interface NfcProtocol {

    // forcer tout ceux qui implémente le nfcprotocol a avoir un listener
    val listener: NfcReaderListener?

    // ce qui veulent savoir lorsqu'on a reussi à lire le contenu d'un tag nfc
    interface NfcReaderListener {
        fun onNfcError(error: Throwable)
        fun onNfcDataReady(data: String)
    }

    fun lockNfc()
    fun unlockNfc()

    fun readNfcTag(intent: Intent)
    fun writeNfcTag(intent: Intent, data: String)
}