package com.cesoft.organizate3.ui.screen.taskdetail

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cesoft.organizate3.R
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.ui.composables.RatingBarCompo
import com.cesoft.organizate3.ui.dateFormatter
import com.cesoft.organizate3.ui.navigation.TASK_ID


@Composable
fun TaskDetailScreen(
    args: Bundle?,
    popBack: () -> Unit
) {
    val viewModel: TaskDetailViewModel = viewModel()
    val id = args?.getInt(TASK_ID) ?: -1

    val task: Task by viewModel.task.collectAsState(Task())

    LaunchedEffect(id) {
        viewModel.sendIntent(TaskDetailViewModel.Intent.Init(id))
    }

    Scaffold(
        topBar = { TopBar(task.name, popBack) }
    ) {
        Body(task)
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

@Composable
private fun Body(task: Task) {
    val modifierTitle = Modifier.padding(8.dp, 16.dp, 0.dp, 0.dp)
    val modifierValue = Modifier.padding(8.dp, 2.dp, 0.dp, 0.dp)

    Column(Modifier.padding(4.dp, 4.dp)) {
        Column {
            FieldTitle(R.string.field_name, modifierTitle)
            FieldValue(task.name, modifierValue)
        }
        Column {
            FieldTitle(R.string.field_description, modifierTitle)
            FieldValue(task.description, modifierValue)
        }
        Column {
            FieldTitle(R.string.field_type, modifierTitle)
            FieldValue(task.type, modifierValue)
        }
        Column {
            FieldTitle(R.string.field_due_date, modifierTitle)
            FieldValue(task.dueDate.dateFormatter(), modifierValue)
        }

        val isDoneTxt = if(task.done)
            stringResource(R.string.task_done)
        else
            stringResource(R.string.task_undone)
        Column {
            FieldTitle(R.string.field_done, modifierTitle)
            FieldValue(isDoneTxt, modifierValue)
        }

        RatingBarCompo(
            value = task.priority.value.toFloat(),
            labelCompo = {FieldTitle(R.string.field_priority, modifier = Modifier.padding(end = 4.dp))},
            modifier = modifierTitle
        )
    }
}

@Composable
private fun FieldTitle(@StringRes text: Int, modifier: Modifier = Modifier) {
    FieldTitle(stringResource(text), modifier)
}

@Composable
private fun FieldTitle(text: String, modifier: Modifier = Modifier) {
    val colorTitle = androidx.compose.ui.graphics.Color.Companion.Blue
    Text(text, color=colorTitle, modifier = modifier)
}

@Composable
private fun FieldValue(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier = modifier, fontSize = 22.sp)
}