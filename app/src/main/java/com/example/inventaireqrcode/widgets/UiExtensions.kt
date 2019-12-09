package com.example.inventaireqrcode.widgets

import android.view.View
import androidx.core.view.ViewCompat
import java.text.SimpleDateFormat
import java.util.*

// formatage de la date
private val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm")

fun Date.toFormattedString() = dateFormatter.format(this)

// fonction d'extension pour les transitions
fun View.setTransitionNameCompat(prefix: String, id: Any) =
    ViewCompat.setTransitionName(this, "$prefix$id")

fun View.getTransitionNameCompat() =
    ViewCompat.getTransitionName(this) ?: ""