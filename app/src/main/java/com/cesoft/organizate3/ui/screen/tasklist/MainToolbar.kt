package com.cesoft.organizate3.ui.screen.tasklist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cesoft.organizate3.R
import com.cesoft.organizate3.ui.composables.DropDownCompo
import com.cesoft.organizate3.ui.navigation.Screens

@Composable
fun MainToolbar(
    suggestions: List<String>,
    onSelect: (String) -> Unit,
    onSearch: () -> Unit
) {
    return Column(Modifier.fillMaxWidth().padding(0.dp)) {
        TopAppBar(
            title = { //TODO: desplegable con todos los tipos de listas de tareas
                Text(text = stringResource(Screens.TasksScreen.label))
            },
            actions = { //TODO: buscar
                IconButton(onClick = onSearch) {
                    Icon(Icons.Filled.Search, stringResource(R.string.search))
                }
            },
            navigationIcon = {
                Column(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Screens.TasksScreen.icon, null)
                }
            })
        DropDownCompo(
            values = suggestions,
            modifier = Modifier.background(MaterialTheme.colors.primary),
            label = R.string.field_type,
            textColor = MaterialTheme.colors.surface,
            onSelect = onSelect)

    }
}
/*
@Composable
fun FilterToolbar(type: String, suggestions: List<String>, onSelect: (String) -> Unit) {
    return TopAppBar(
        title = {
            //TODO: desplegable con todos los tipos de listas de tareas
            //DropdownMenuItem()
        },
        actions = {
            TextFieldAutoCompo(
                type, R.string.field_type, suggestions
            ) {
                onSelect(it)
            }
        },
        navigationIcon = {
            //Column(
            //    modifier = Modifier.fillMaxSize(),
            //    verticalArrangement = Arrangement.Center,
            //    horizontalAlignment = Alignment.CenterHorizontally) {
            //    Icon(Screens.TasksScreen.icon, null)
            //}
        }
    )
}*/