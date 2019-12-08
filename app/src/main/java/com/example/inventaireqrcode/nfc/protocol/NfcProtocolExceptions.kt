package com.example.inventaireqrcode.nfc.protocol

import java.lang.Exception

open class NfcProtocolExceptions(message: String, cause: Throwable? = null) : Exception(message, cause)

class DisabledNfcException(message: String, cause: Throwable? = null) : NfcProtocolExceptions(message, cause)