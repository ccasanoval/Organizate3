package com.cesoft.organizate3.ui.composables

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cesoft.organizate3.R

@Composable
private fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.fillMaxWidth(),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedString(it)
                }
            ) {
                Text(it, modifier = Modifier.wrapContentWidth())
            }
        }
    }
}

@Composable
fun DropDownCompo(
    currentValue: String,
    values: List<String>,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colors.primary,
    @StringRes label: Int? = null,
    onSelect: (String) -> Unit
) {
    val text = remember { mutableStateOf(currentValue) }
    val isOpen = remember { mutableStateOf(false) }
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    val userSelectedString: (String) -> Unit = {
        text.value = it
        onSelect(it)
    }
    Box(modifier = modifier) {
        Column {
            OutlinedTextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { label?.let { Text(text = stringResource(label))} },
                modifier = Modifier.fillMaxWidth(),
                //colors = TextFieldDefaults.outlinedTextFieldColors()
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = textColor,
                    focusedLabelColor = textColor,
                    unfocusedLabelColor = textColor
                )
            )
            DropDownList(
                requestToOpen = isOpen.value,
                list = values,
                openCloseOfDropDownList,
                userSelectedString
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(onClick = { isOpen.value = true })
        )
    }
}

@Composable
fun IntDropDownCompo(values: List<Int>, valuesTxt: List<String>, onSelect: (Int) -> Unit) {
    val text = remember { mutableStateOf("") }
    val isOpen = remember { mutableStateOf(false) }
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    val userSelectedString: (String) -> Unit = {
        text.value = it
    }
    Box {
        Column {
            OutlinedTextField(
                value = text.value,
                onValueChange = {
                    text.value = it
                    val i = valuesTxt.indexOf(it)
                    onSelect(values[i])
                },
                label = { Text(text = stringResource(R.string.field_radius)) },
                modifier = Modifier.fillMaxWidth()
            )
            DropDownList(
                requestToOpen = isOpen.value,
                list = valuesTxt,
                openCloseOfDropDownList,
                userSelectedString
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(onClick = { isOpen.value = true })
        )
    }
}
