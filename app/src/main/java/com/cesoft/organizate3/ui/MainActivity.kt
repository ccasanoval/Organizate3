package com.cesoft.organizate3.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.cesoft.organizate3.ui.screen.tasklist.MainScreen
import com.cesoft.organizate3.ui.theme.Organizate3Theme

//TODO:
// Make map work as expected
// Change dueDate Field....
// Task importance rating with stars
// Get current position for tasks
// Radius in new task, and show in detail
// Geofencing alerts
// Make compatible with Huawei maps also (HMS)
// Photo for tasks
//

class MainActivity : AppCompatActivity() {// ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Organizate3Theme {
                MainScreen()
            }
        }
    }
}
