package com.cesoft.organizate3.ui.screen.taskdetail

import android.os.Bundle
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
import com.cesoft.organizate3.ui.navigation.TASK_ID
import com.cesoft.organizate3.ui.navigation.TASK_NAME

@Composable
fun TaskDetailScreen(
    args: Bundle?,
    popBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val id = args?.getInt(TASK_ID) ?: 0
    val name = args?.getString(TASK_NAME) ?: "?"

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
