package com.cesoft.organizate3.ui.screen.taskdetail

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cesoft.organizate3.R
import com.cesoft.organizate3.domain.UseCaseResult
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.ui.composables.LoadingCompo
import com.cesoft.organizate3.ui.composables.MapCompo
import com.cesoft.organizate3.ui.composables.MapState
import com.cesoft.organizate3.ui.composables.RatingBarCompo
import com.cesoft.organizate3.ui.dateFormatter
import com.cesoft.organizate3.ui.navigation.TASK_ID
import com.google.android.libraries.maps.model.LatLng
import kotlinx.coroutines.launch

@Composable
fun TaskDetailScreen(args: Bundle?, popBack: () -> Unit) {
    val id = args?.getInt(TASK_ID) ?: -1
    val viewModel: TaskDetailViewModel = viewModel()
    LaunchedEffect(id) {
        viewModel.sendIntent(Intent.Init(id))
    }
    TaskDetailContent(viewModel, popBack)
}

@Composable
private fun TaskDetailContent(viewModel: TaskDetailViewModel, popBack: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val state: State by viewModel.state.collectAsState(viewModel.stateInit)
    var task: Task? = null
    when(state) {
        is State.Fetch -> {
            val fetch = state as State.Fetch
            when(fetch.res) {
                UseCaseResult.Loading -> {
                    android.util.Log.e("TaskDetailView", "fetch----------loading ")
                    LoadingCompo()
                }
                is UseCaseResult.Success -> {
                    android.util.Log.e("TaskDetailView", "fetch----------success "+fetch.res.data)
                    task = fetch.res.data
                }
                is UseCaseResult.Error -> {
                    android.util.Log.e("TaskDetailView", "fetch----------error "+fetch.res.exception)
                    ErrorTaskNotFound()
                }
            }
        }
        is State.Delete -> {
            val fetch = state as State.Delete
            when(fetch.res) {
                is UseCaseResult.Loading -> {
                    android.util.Log.e("TaskDetailView", "delete----------loading")
                    LoadingCompo()
                }
                is UseCaseResult.Success -> {
                    android.util.Log.e("TaskDetailView", "delete----------success"+fetch.res.data)
                    popBack()
                }
                is UseCaseResult.Error -> {
                    android.util.Log.e("TaskDetailView", "delete----------error "+fetch.res.exception)
                    ErrorTaskDelete()
                }
            }
        }
    }

    val showDialog = remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopBar(task?.name ?: "?", popBack, { showDialog.value = true }) }
    ) {
        if(showDialog.value) {
            DeleteConfirmDialog(showDialog) {
                coroutineScope.launch {
                    viewModel.sendIntent(Intent.Delete)
                }
            }
        }
        else {
            task?.let {
                Body(task)
            } ?: run {
                ErrorTaskNotFound()
            }
        }
    }
}

@Composable
private fun DeleteConfirmDialog(showDialog: MutableState<Boolean>, onOk: () -> Unit) {
    AlertDialog(
        title = { Text(stringResource(R.string.ask_delete_task_title)) },
        text = { Text(stringResource(R.string.ask_delete_task_body)) },
        confirmButton = {
            Button(
                onClick = {
                    showDialog.value = false
                    onOk()
                }) {
                Text(stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    showDialog.value = false
                }) {
                Text(stringResource(android.R.string.cancel))
            }
        },
        onDismissRequest = { showDialog.value = false },
    )
}

@Composable
private fun TopBar(
    name: String,
    popBack: () -> Unit,
    onDelete: () -> Unit
) {
    TopAppBar(
        title = { Text(name) },
        navigationIcon = {
            IconButton(onClick = { popBack() }) {
                Icon(Icons.Filled.ArrowBack, stringResource(R.string.back))
            }
        },
        actions = {
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, stringResource(R.string.delete))
            }
        }
    )
}

@Composable
private fun Body(task: Task) {
    val modifierTitle = Modifier.padding(8.dp, 16.dp, 0.dp, 0.dp)
    val modifierValue = Modifier.padding(8.dp, 2.dp, 0.dp, 0.dp)

    //TODO: Scroll as in edit
    Column(
        Modifier
            .padding(4.dp, 4.dp) //.background(Color.LightGray)
            .verticalScroll(rememberScrollState())
    ) {

        // Name
        Column {
            FieldTitle(R.string.field_name, modifierTitle)
            FieldValue(task.name, modifierValue)
        }

        // Description
        Column {
            FieldTitle(R.string.field_description, modifierTitle)
            FieldValue(task.description, modifierValue)
        }

        // Type
        Column {
            FieldTitle(R.string.field_type, modifierTitle)
            FieldValue(task.type, modifierValue)
        }

        // Due date
        Column {
            FieldTitle(R.string.field_due_date, modifierTitle)
            FieldValue(task.dueDate.dateFormatter(), modifierValue)
        }

        // Done
        val isDoneTxt = if(task.done) stringResource(R.string.task_done)
        else stringResource(R.string.task_undone)
        Column {
            FieldTitle(R.string.field_done, modifierTitle)
            FieldValue(isDoneTxt, modifierValue)
        }

        // Priority
        RatingBarCompo(
            value = task.priority.value.toFloat(),
            labelCompo = { FieldTitle(R.string.field_priority, modifier = Modifier.padding(end = 4.dp)) },
            modifier = modifierTitle
        )

        // Map
        val latLng = LatLng(task.latitude, task.longitude)
        val radius = task.radius +200
        val zoom = when {
            radius > 10000 -> 8f
            radius > 1000 -> 12f
            radius > 100 -> 14f
            else -> 16f
        }
        val mapState = MapState(latLng = latLng, zoom = zoom, marker = null, readOnly = true)
        MapCompo(value = latLng, radius = radius, mapState = mapState)

        // Spacer: bottom toolbar height so it doesnt hide map
        Column {
            Spacer(Modifier.size(60.dp)) //TODO: Get bottom toolbar height
        }
    }
}

@Composable
private fun FieldTitle(@StringRes text: Int, modifier: Modifier = Modifier) {
    FieldTitle(stringResource(text), modifier)
}

@Composable
private fun FieldTitle(text: String, modifier: Modifier = Modifier) {
    val color = androidx.compose.ui.graphics.Color.Companion.Blue
    SelectionContainer {
        Text(text, color = color, modifier = modifier)
    }
}

@Composable
private fun FieldValue(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier = modifier, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    //fontFamily = FontFamily.SansSerif
}

@Composable
private fun ErrorTaskNotFound() {
    val color = androidx.compose.ui.graphics.Color.Companion.Red//TODO: Themes (dark mode)
    val text = stringResource(R.string.error_unkown_task)
    Text(text, color = color, fontSize = 26.sp, fontWeight = FontWeight.Bold)
}

@Composable
private fun ErrorTaskDelete() {
    val color = androidx.compose.ui.graphics.Color.Companion.Red
    val text = stringResource(R.string.error_delete_task)
    Text(text, color = color, fontSize = 26.sp, fontWeight = FontWeight.Bold)
}
