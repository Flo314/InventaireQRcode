package com.example.inventaireqrcode.nfc.protocol

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

interface NfcProtocol : LifecycleObserver{

    // forcer tout ceux qui implémente le nfcprotocol a avoir un listener
    val listener: NfcReaderListener?

    // ce qui veulent savoir lorsqu'on a reussi à lire le contenu d'un tag nfc
    interface NfcReaderListener {
        fun onNfcError(error: Throwable)
        fun onNfcDataReady(data: String)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun lockNfc()
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun unlockNfc()

    fun readNfcTag(intent: Intent)
    fun writeNfcTag(intent: Intent, data: String)
}