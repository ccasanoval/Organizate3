package com.cesoft.organizate3.ui.screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.cesoft.organizate3.R

@Composable
fun TaskDetailScreen(
    id: String,
    name: String,
    popBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(id) {
        //viewModel.setTask(id)
        android.util.Log.e("TaskDetailScreen", "--------------------LaunchedEffect($id)")
    }

    Scaffold(
        topBar = { TopBar(name, popBack) }
    ) {
        Text(name)
    }
}

@Composable
private fun TopBar(name: String, popBack: () -> Unit) {
    TopAppBar(
        title = { Text(name) },
        navigationIcon = {
            IconButton(onClick = { popBack() }) {
                Icon(Icons.Filled.ArrowBack, stringResource(R.string.back))
            }
        }
    )
}
