package com.cesoft.organizate3.ui.composables

import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cesoft.organizate3.R
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


//https://github.com/vanpra/compose-material-dialogs
@Composable
fun DatePickerCompo(value: Date, @StringRes label: Int, onDateSelected: (Date) -> Unit) {
    Column {
        Text(stringResource(label), Modifier.padding(8.dp, 16.dp, 0.dp, 0.dp))
        AndroidView(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp), factory = { context ->
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

@ExperimentalComposeUiApi
@Composable
fun DateFieldCompo(
    value: Date? = null,
    @StringRes label: Int = R.string.field_due_date,
    updatedDate: (date: Date?) -> Unit,
) {
    val activity = LocalContext.current as AppCompatActivity
    fun showDatePicker(updatedDate: (Date?) -> Unit) {
        val picker = MaterialDatePicker.Builder.datePicker().build()
        picker.show(activity.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            updatedDate(Date(it))
        }
    }
    fun Date.dateFormatter() : String {
        this.time.let {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            return formatter.format(calendar.time)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .padding(top = 10.dp)
            .border(0.5.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            .clickable {
                showDatePicker(updatedDate)
            }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (lable, iconView) = createRefs()
            Text(
                text = value?.dateFormatter() ?: stringResource(label),
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(lable) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(iconView.start)
                        width = Dimension.fillToConstraints
                    }
            )
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .constrainAs(iconView) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}
