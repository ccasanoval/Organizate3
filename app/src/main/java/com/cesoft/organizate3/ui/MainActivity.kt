package com.cesoft.organizate3.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import com.cesoft.organizate3.ui.screen.tasklist.TaskListScreen
import com.cesoft.organizate3.ui.theme.Organizate3Theme
import dagger.hilt.android.AndroidEntryPoint

//TODO:
// Order tasks by: dueDate, geolocation, priority, in the main list!
// Get current geolocation for tasks
// Geofencing alerts
// Make compatible with Huawei maps also (HMS)
// Photo for tasks
// Dark Mode
// Pagination?
// Tests?

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
