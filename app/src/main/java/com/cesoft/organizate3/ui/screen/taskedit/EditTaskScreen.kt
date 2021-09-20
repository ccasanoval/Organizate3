package com.cesoft.organizate3.ui.screen.taskedit

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cesoft.organizate3.R
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.ui.composables.CheckboxCompo
import com.cesoft.organizate3.ui.composables.DateFieldCompo
import com.cesoft.organizate3.ui.composables.LoadingCompo
import com.cesoft.organizate3.ui.composables.MapCompo
import com.cesoft.organizate3.ui.composables.RadiusCompo
import com.cesoft.organizate3.ui.composables.RatingBarCompo
import com.cesoft.organizate3.ui.composables.TextFieldAutoCompo
import com.cesoft.organizate3.ui.composables.TextFieldCompo
import com.cesoft.organizate3.ui.navigation.Screens
import com.cesoft.organizate3.ui.navigation.TASK_ID
import com.cesoft.organizate3.ui.screen.MainBottomNavigation
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Date

@ExperimentalComposeUiApi
@Composable
fun EditTaskScreen(
    navController: NavHostController,
    args: Bundle?=null,
    viewModel: EditTaskViewModel=hiltViewModel()//viewModel()
) {
    //val viewModel: EditTaskViewModel by viewModel()
    val state: State by viewModel.state.collectAsState(State.Loading)

    val id = args?.getInt(TASK_ID)
    LaunchedEffect(id) {
        viewModel.sendIntent(Intent.Init(id))
    }

    val coroutineScope = rememberCoroutineScope()
    val onSave: () -> Unit = {
        coroutineScope.launch {
            viewModel.sendIntent(Intent.Save)
        }
    }
    when(val s = state) {
        State.Loading -> Loading()
        State.Saved -> saved(viewModel, navController) //Saved(viewModel, navController)
        is State.Editing -> {
            val title = s.taskName ?: stringResource(Screens.AddTaskScreen.label)
            Editing(title, navController, onSave)
        }
        is State.Error -> {
            val title = if(s.isNewTask)
                stringResource(Screens.AddTaskScreen.label)
            else
                stringResource(R.string.error_unkown_task)
            Error(title, navController, onSave)
        }
    }
}

@Composable private fun Loading() {
    LoadingCompo()
}

@ExperimentalComposeUiApi
@Composable
private fun Editing(title: String, navController: NavHostController, onSave: () -> Unit) {
    Scaffold(
        topBar = { TopBar(title, onSave) },
        bottomBar = { MainBottomNavigation(navController) }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())) {
            Body()
        }
    }
}

@Composable private fun Saved(viewModel: EditTaskViewModel, navController: NavHostController) {
    navController.navigate(Screens.TasksScreen.route) {
        popUpTo(Screens.AddTaskScreen.route) { inclusive = true }
    }
    LaunchedEffect(true) {
        viewModel.sendIntent(Intent.Done)
    }
}

private fun saved(viewModel: EditTaskViewModel, navController: NavHostController) {
    MainScope().launch {
        navController.navigate(Screens.TasksScreen.route) {
            popUpTo(Screens.AddTaskScreen.route) { inclusive = true }
        }
        viewModel.sendIntent(Intent.Done)
    }
}

@Composable private fun Error(title: String, navController: NavHostController, onSave: () -> Unit) {
    Scaffold(topBar = { TopBar(title, onSave) }, bottomBar = { MainBottomNavigation(navController) }) {
        val color = Color.Red
        val text = stringResource(R.string.error_save_task)
        Text(text, color = color, fontSize = 26.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable private fun TopBar(title: String, onSave: () -> Unit) {
    TopAppBar(title = { Text(title) }, actions = {
        IconButton(onSave) {
            Icon(Icons.Filled.Save, stringResource(R.string.save))
        }
    }, navigationIcon = {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Screens.AddTaskScreen.icon, contentDescription = null
            )
        }
    })
}

@ExperimentalComposeUiApi @Preview @Composable private fun Body() {
    val viewModel: EditTaskViewModel = viewModel()

    val name: String by viewModel.name.collectAsState()
    val description: String by viewModel.description.collectAsState()
    val type: String by viewModel.type.collectAsState()
    val dueDate: Date by viewModel.dueDate.collectAsState()
    val done: Boolean by viewModel.done.collectAsState()
    val priority: Task.Priority by viewModel.priority.collectAsState()
    val latLng: LatLng by viewModel.latLng.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val changeField: (field: Field, value: Any?) -> Unit = { field, value ->
        coroutineScope.launch {
            viewModel.sendIntent(Intent.ChangeField(field, value))
        }
    }
    val suggestions = viewModel.suggestions.collectAsState()

    Column(
        Modifier //.background(Color.LightGray)
            .verticalScroll(rememberScrollState())
    ) {

        // Name
        TextFieldCompo(name, R.string.field_name) {
            changeField(Field.Name, it)
        }

        // Description
        TextFieldCompo(description, R.string.field_description) {
            changeField(Field.Description, it)
        }

        // Type
        TextFieldAutoCompo(
            type, R.string.field_type, suggestions.value
        ) {
            changeField(Field.Type, it)
        }

        // Date picker
        //DatePickerCompo(dueDate, R.string.field_due_date) {
        DateFieldCompo(dueDate, R.string.field_due_date) {
            changeField(Field.DueDate, it)
        }

        //TODO: Better box and priority float at end of line...
        Row { // Done
            CheckboxCompo(done, R.string.field_done) {
                changeField(Field.Done, it)
            }

            // Priority
            RatingBarCompo(
                modifier = Modifier.padding(8.dp, 12.dp),
                value = priority.value.toFloat(),
                label = R.string.field_priority,
            ) {
                changeField(Field.Priority, it)
            }
        }

        // Radius
        RadiusCompo()

        // Map
        MapCompo(latLng, viewModel.mapState) { latLng ->
            changeField(Field.LatLon, latLng)
        }
    }
}
