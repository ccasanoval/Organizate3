package com.cesoft.organizate3.ui.screen.taskadd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cesoft.organizate3.R
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.ui.composables.CheckboxCompo
import com.cesoft.organizate3.ui.composables.DateFieldCompo
import com.cesoft.organizate3.ui.composables.MapCompo
import com.cesoft.organizate3.ui.composables.RatingBarCompo
import com.cesoft.organizate3.ui.composables.TextFieldAutoCompo
import com.cesoft.organizate3.ui.composables.TextFieldCompo
import com.cesoft.organizate3.ui.navigation.Screens
import com.cesoft.organizate3.ui.screen.MainBottomNavigation
import kotlinx.coroutines.launch
import java.util.Date
import com.cesoft.organizate3.ui.screen.taskadd.AddTaskViewModel.Intent
import com.cesoft.organizate3.ui.screen.taskadd.AddTaskViewModel.Field
import com.google.android.libraries.maps.model.LatLng


@ExperimentalComposeUiApi
@Composable
fun AddTaskScreen(navController: NavHostController) {
    android.util.Log.e("AddTaskScreen", "AddTaskScreen----------------------0")
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { MainBottomNavigation(navController) }
    ) {
        Body()
    }
}

@Composable
private fun TopBar() {
    val viewModel: AddTaskViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val onSave: () -> Unit = {
        coroutineScope.launch {
            viewModel.sendIntent(Intent.Save)
        }
    }
    TopAppBar(
        title = { Text(stringResource(Screens.AddTaskScreen.label)) },
        actions = {
            IconButton(onSave) {
                Icon(Icons.Filled.Save, stringResource(R.string.save))
            }
        },
        navigationIcon = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Screens.AddTaskScreen.icon, contentDescription = null
                )
            }
        }
    )
}

@ExperimentalComposeUiApi
@Preview
@Composable
private fun Body() {
    android.util.Log.e("AddTaskScreen", "Body----------------------0")

    val viewModel: AddTaskViewModel = viewModel()
    val name: String by viewModel.name.collectAsState()
    val description: String by viewModel.description.collectAsState()
    val type: String by viewModel.type.collectAsState()
    val dueDate: Date by viewModel.dueDate.collectAsState()
    val done: Boolean by viewModel.done.collectAsState()
    val priority: Task.Priority by viewModel.priority.collectAsState()
    val latLng: LatLng by viewModel.latLng.collectAsState()
    //val zoom: Float by viewModel.zoom.collectAsState()
    //val marker: Marker? by viewModel.marker.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val changeField : (field: Field, value: Any?) -> Unit = { field, value ->
        coroutineScope.launch {
            viewModel.sendIntent(Intent.ChangeField(field, value))
        }
    }
    val suggestions = viewModel.suggestions.collectAsState()

    Column(
        Modifier
            .background(Color.LightGray)
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
            android.util.Log.e("AddTaskScreen", "Body----------------------date=$it")
        }

        //TODO: Better box and priority float at end of line...
        Row {
            // Done
            CheckboxCompo(done, R.string.field_done) {
                changeField(Field.Done, it)
            }

            // Priority
            //RatingBarCompo(priority.value.toFloat())
            RatingBarCompo(
                modifier = Modifier.padding(8.dp, 12.dp),
                value = priority.value.toFloat(),
                label = R.string.field_priority,
                onValueChange = { android.util.Log.e("Rat", "--------------- onValueChange = $it") }
            ) {
                android.util.Log.e("Rat", "--------------- onRatingChange = $it")
                changeField(Field.Priority, it)
            }
        }

        //TODO: Radius

        // Map
        MapCompo(latLng, viewModel.mapState) { latLng ->
            changeField(Field.LatLon, latLng)
        }

        // Spacer: bottom toolbar height so it doesnt hide map
        Column {
            Spacer(Modifier.size(60.dp))//TODO: Get bottom toolbar height
        }
    }
}
