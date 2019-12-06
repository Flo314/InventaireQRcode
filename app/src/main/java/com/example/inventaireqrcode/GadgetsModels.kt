package com.example.inventaireqrcode

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Classe Métier
 */
sealed class Gadget {
    abstract var id : Int
    abstract var dateCreated: Date
}

@Parcelize
data class GadgetQRCode(override var id: Int = 0,
                        override var dateCreated: Date = Date(),
                        val url: String): Gadget(), Parcelable

@Parcelize
data class GadgetNFC(override var id: Int = 0,
                        override var dateCreated: Date = Date(),
                        val url: String): Gadget(), Parcelable