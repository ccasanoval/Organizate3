package com.cesoft.organizate3.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import com.cesoft.organizate3.ui.screen.tasklist.TaskListScreen
import com.cesoft.organizate3.ui.theme.Organizate3Theme
import dagger.hilt.android.AndroidEntryPoint

//TODO:
// When adding or deleting a task, suggestions must be reloaded !?
// Search by name or description
// Order tasks by: dueDate, geolocation, priority, in the main list!
// Get current geolocation for tasks : Workmanager? https://developer.android.com/topic/libraries/architecture/workmanager/basics
//                                                  https://dev.to/bigyan4424/workmanager-for-background-location-updates-fog
// Geofencing alerts
// Photo for tasks
// Dark Mode
// Pagination?
// Tests?
// Enhance README.md
// Make compatible with Huawei maps also (HMS)

@AndroidEntryPoint
@ExperimentalComposeUiApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Organizate3Theme {
                TaskListScreen()
            }
        }
    }
}
