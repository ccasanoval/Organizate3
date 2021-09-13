package com.cesoft.organizate3.ui

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