package com.cesoft.organizate3.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import com.cesoft.organizate3.ui.screen.tasklist.MainScreen
import com.cesoft.organizate3.ui.theme.Organizate3Theme

//TODO:
// Task importance rating with stars
// Get current position for tasks
// Radius in new task, and show in detail
// Geofencing alerts
// Make compatible with Huawei maps also (HMS)
// Photo for tasks
//

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
