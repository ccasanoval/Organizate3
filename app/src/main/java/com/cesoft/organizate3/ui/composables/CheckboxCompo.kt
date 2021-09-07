package com.cesoft.organizate3.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cesoft.organizate3.R


@Composable
fun CheckboxCompo(
    value: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    Row(modifier = Modifier.padding(8.dp, 0.dp)) {
        var checked by remember { mutableStateOf(value) }
        Text(stringResource(R.string.field_done))
        Checkbox(
            modifier = Modifier.padding(8.dp, 0.dp),
            checked = checked,
            onCheckedChange = {
                checked = it
                onValueChange(it)
            }
        )
    }
}