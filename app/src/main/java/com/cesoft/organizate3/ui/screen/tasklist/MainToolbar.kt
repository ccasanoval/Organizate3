package com.cesoft.organizate3.ui.screen.tasklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cesoft.organizate3.R
import com.cesoft.organizate3.ui.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun MainToolbar(viewModel: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val onSearch: () -> Unit = {
        coroutineScope.launch {
            viewModel.sendIntent(MainViewModel.Intent.Search)
        }
    }
    return TopAppBar(
        title = {
            //TODO: desplegable con todos los tipos de listas de tareas
            Text(text = stringResource(Screens.TasksScreen.label))
        },
        actions = {
            //TODO: buscar
            IconButton(onClick = onSearch) {
                Icon(Icons.Filled.Search, stringResource(R.string.search))
            }
        },
        navigationIcon = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Screens.TasksScreen.icon, null)
            }
        }
    )
}