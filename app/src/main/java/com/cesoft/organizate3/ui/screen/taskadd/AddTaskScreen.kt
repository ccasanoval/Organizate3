package com.cesoft.organizate3.ui.screen.taskadd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cesoft.organizate3.R
import com.cesoft.organizate3.ui.composables.CheckboxCompo
import com.cesoft.organizate3.ui.composables.DatePickerCompo
import com.cesoft.organizate3.ui.composables.MapCompo
import com.cesoft.organizate3.ui.composables.TextFieldAutoCompo
import com.cesoft.organizate3.ui.composables.TextFieldCompo
import com.cesoft.organizate3.ui.navigation.Screens
import com.cesoft.organizate3.ui.screen.MainBottomNavigation
import kotlinx.coroutines.launch
import java.util.Date
import com.cesoft.organizate3.ui.screen.taskadd.AddTaskViewModel.Intent
import com.cesoft.organizate3.ui.screen.taskadd.AddTaskViewModel.Field
import com.google.android.libraries.maps.model.LatLng


@Composable
fun AddTaskScreen(navController: NavHostController) {
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

@Preview
@Composable
private fun Body() {
    val viewModel: AddTaskViewModel = viewModel()
    val name: String by viewModel.name.collectAsState()
    val description: String by viewModel.description.collectAsState()
    val type: String by viewModel.type.collectAsState()
    val dueDate: Date by viewModel.dueDate.collectAsState()
    val done: Boolean by viewModel.done.collectAsState()
    val latLng: LatLng by viewModel.latLng.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val changeField : (field: Field, value: Any) -> Unit = { field, value ->
        coroutineScope.launch {
            viewModel.sendIntent(Intent.ChangeField(field, value))
        }
    }

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
            type, R.string.field_type, listOf("Default", "Shopping list", "Learn") //TODO:from db
        ) {
            changeField(Field.Type, it)
        }
        // Date picker
        DatePickerCompo(dueDate, R.string.field_due_date) {
            changeField(Field.DueDate, it)
            android.util.Log.e("AddTaskScreen", "Body----------------------date=$it")
        }
        // Done
        CheckboxCompo(done) {
            changeField(Field.Done, it)
        }
        //TODO: priority
        //TODO: Radius
        // Map
        MapCompo(latLng) {
            changeField(Field.LatLon, it)
        }
        // Spacer: bottom toolbar height so it doesnt hide map
        Column {
            Spacer(Modifier.size(70.dp))
        }
    }
}
