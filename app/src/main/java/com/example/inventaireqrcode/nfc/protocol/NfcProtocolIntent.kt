package com.example.inventaireqrcode.nfc.protocol

import android.app.Activity
import android.content.Intent
import android.nfc.NfcAdapter

class NfcProtocolIntent(
    override val listener: NfcProtocol.NfcReaderListener?,
    private val activity: Activity)
    : NfcProtocol {

    private enum class State {
        IDLE,
        INIT,
        LOCKED,
        UNLOCKED

    }

    private var state = State.IDLE
    private var nfcAdapter: NfcAdapter? = null

    private fun init() : Boolean {
        if (nfcAdapter == null || !nfcAdapter!!.isEnabled) {
            nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
// dès qu'on récupère le nfcadapter on doit revérifier si il est null
            if (nfcAdapter == null || !nfcAdapter!!.isEnabled) {
                listener?.onNfcError(DisabledNfcException("Cannot initialize NFC adapter"))
                return false
            }
        }

        return true
    }

    override fun lockNfc() {
        init()
    }

    override fun unlockNfc() {

    }

    override fun readNfcTag(intent: Intent) {

    }

    override fun writeNfcTag(intent: Intent, data: String) {

    }
}