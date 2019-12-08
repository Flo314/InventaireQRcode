package com.example.inventaireqrcode.nfc

import android.content.Intent
import android.provider.Settings

fun nfcSettingsIntent() : Intent{
    return when {
        android.os.Build.VERSION.SDK_INT >= 16 -> Intent(Settings.ACTION_NFC_SETTINGS)
        else -> Intent(Settings.ACTION_WIRELESS_SETTINGS)
    }
}