package com.example.inventaireqrcode.widgets

import java.text.SimpleDateFormat
import java.util.*

// formatage de la date
private val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm")

fun Date.toFormattedString() = dateFormatter.format(this)