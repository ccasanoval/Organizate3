package com.cesoft.organizate3.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cesoft.organizate3.ui.screen.tasklist.MainScreen
import com.cesoft.organizate3.ui.theme.Organizate3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Organizate3Theme {
                MainScreen()
            }
        }
    }
}
