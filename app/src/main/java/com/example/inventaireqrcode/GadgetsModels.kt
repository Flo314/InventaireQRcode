package com.example.inventaireqrcode

import java.util.*

/**
 * Classe Métier
 */
sealed class Gadget {
    abstract var id : Int
    abstract var dateCreated: Date
}

data class GadgetQRCode(override var id: Int = 0,
                        override var dateCreated: Date = Date(),
                        val url: String): Gadget()

data class GadgetNFC(override var id: Int = 0,
                        override var dateCreated: Date = Date(),
                        val url: String): Gadget()