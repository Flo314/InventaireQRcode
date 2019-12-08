package com.example.inventaireqrcode.nfc.protocol

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import timber.log.Timber

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
    private var pendingIntent: PendingIntent? = null

    private fun init() : Boolean {
        if (state != State.IDLE) {
            Timber.w("Invalid state for init. state=$state")
            return false
        }
        if (nfcAdapter == null || !nfcAdapter!!.isEnabled) {
            nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
// dès qu'on récupère le nfcadapter on doit revérifier si il est null
            if (nfcAdapter == null || !nfcAdapter!!.isEnabled) {
                listener?.onNfcError(DisabledNfcException("Cannot initialize NFC adapter"))
                return false
            }
        }

        pendingIntent = PendingIntent.getActivity(activity, 0,
            Intent(activity, activity.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)

        switchToState(State.INIT)
        return true
    }

    private fun switchToState(state: State) {
        // voir tous les changements d'états
        Timber.d("Switching from ${this.state} to $state")
        this.state = state
    }

    override fun lockNfc() {
        if (state == State.IDLE) {
            if (!init()) {
                init()
            }
        }
        val intentFilters = arrayOf(
            IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
            IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED),
            IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        )
        nfcAdapter?.enableForegroundDispatch(activity, pendingIntent, intentFilters, null)
        switchToState(State.LOCKED)
    }

    override fun unlockNfc() {

    }

    override fun readNfcTag(intent: Intent) {

    }

    override fun writeNfcTag(intent: Intent, data: String) {

    }
}