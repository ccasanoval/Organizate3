package com.cesoft.organizate3.ui

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

fun Date.dateFormatter() : String {
    this.time.let {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        return formatter.format(calendar.time)
    }
}

fun Date.dateColor() : Color {
    this.time.let {
        val t = it - Date().time
        return when {
            t > 2*24*3600 -> {
                Color.Green
            }
            t < -2*24*3600 -> {
                Color.Red
            }
            else -> {
                Color.Yellow
            }
        }
    }
}