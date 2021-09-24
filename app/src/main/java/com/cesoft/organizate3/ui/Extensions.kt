package com.cesoft.organizate3.ui

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
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
                Color.Cyan
            }
        }
    }
}

@FlowPreview
suspend fun <T> Flow<List<T>>.flattenToList() =
    flatMapConcat { it.asFlow() }.toList()