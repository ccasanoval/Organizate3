package com.cesoft.organizate3.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import com.cesoft.organizate3.ui.screen.tasklist.MainScreen
import com.cesoft.organizate3.ui.theme.Organizate3Theme

//TODO:
// Remove AddTask and use EditTask...
// Tests to check if editTask screen works and initializes from task passed...
// DI
// Get current geolocation for tasks
// Geofencing alerts
// Make compatible with Huawei maps also (HMS)
// Photo for tasks
// Order tasks by: dueDate, geolocation, priority, in the main list!

@ExperimentalComposeUiApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Organizate3Theme {
                MainScreen()
            }
        }
    }
}
