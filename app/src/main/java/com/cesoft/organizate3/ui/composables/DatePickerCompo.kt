package com.cesoft.organizate3.ui.composables

import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.cesoft.organizate3.R
import java.util.*


//https://github.com/vanpra/compose-material-dialogs
@Composable
fun DatePickerCompo(value: Date, label: Int, onDateSelected: (Date) -> Unit) {
    Column {
        Text(stringResource(label), Modifier.padding(8.dp, 16.dp, 0.dp, 0.dp))
        AndroidView(modifier = Modifier.fillMaxWidth().padding(0.dp), factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.datepicker, null)
            val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
            val calendar = Calendar.getInstance()
            calendar.time = value //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //    datePicker.isForceDarkAllowed = true
            //}
            datePicker.init(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
            ) { _, year, monthOfYear, dayOfMonth ->
                val date = Calendar.getInstance().apply {
                    set(year, monthOfYear, dayOfMonth)
                }.time
                onDateSelected(date)
            }
            datePicker
        })
    }
}
