package com.cesoft.organizate3.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.PopupProperties


@Composable
fun TextFieldCompo(
    value: String,
    label: Int,
    onValueChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),//.requiredHeight(24.dp),
            label = { Text(stringResource(label)) }
        )
    }
}

@Composable
fun TextFieldAutoCompo(
    value: String,
    label: Int,
    suggestions: List<String>,
    onValueChange: (String) -> Unit
) {
    Column {
        var data by remember { mutableStateOf(value) }
        AutoCompleteText(
            value = data,
            label = { Text(stringResource(label)) },
            allSuggestions = suggestions,
            onValueChange = {
                data = it
                onValueChange(it)
            }
        )
        //OutlinedTextField(
        //    value = value,
        //    onValueChange = { value = it },
        //    modifier = Modifier.fillMaxWidth(),
        //    label = {Text(stringResource(R.string.field_type))}
        //)
    }
}

@Composable
private fun AutoCompleteText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    allSuggestions: List<String> = emptyList()
) {
    val suggestions by remember { mutableStateOf(mutableListOf<String>()) }
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { text ->
                if(text !== value) {
                    onValueChange(text)
                    if(text.length > 1) {
                        suggestions.clear()
                        for(sug in allSuggestions) {
                            if(sug.contains(text, ignoreCase = true)) {
                                suggestions.add(sug)
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = label,
        )
        DropdownMenu(
            expanded = suggestions.isNotEmpty(),
            onDismissRequest = { },
            modifier = Modifier.fillMaxWidth(),
            properties = PopupProperties(focusable = false)
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    onValueChange(label)
                    suggestions.clear()
                }) {
                    Text(text = label)
                }
            }
        }
    }
}
