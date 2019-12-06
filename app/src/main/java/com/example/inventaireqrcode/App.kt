package com.example.inventaireqrcode

import android.app.Application
import com.example.inventaireqrcode.repository.Repo
import timber.log.Timber

/**
 * Log accessible de partout
 * centralisé l'accès au repo pour avoir qu'une seule instance
 */
class App: Application() {

    companion object {
        val repo = Repo()
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}