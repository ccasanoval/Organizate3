package com.cesoft.organizate3.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cesoft.organizate3.ui.screen.taskdetail.TaskDetailScreen
import com.cesoft.organizate3.ui.screen.taskedit.EditTaskScreen
import com.cesoft.organizate3.ui.screen.taskedit.EditTaskViewModel
import com.cesoft.organizate3.ui.screen.tasklist.State
import com.cesoft.organizate3.ui.screen.tasklist.TasksView
import dagger.hilt.android.lifecycle.HiltViewModel


@ExperimentalComposeUiApi
@Composable
fun Navigator(
    state: State,
    navController: NavHostController,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    android.util.Log.e("MainV", "Navigator --------********------------ $state")

    //TODO: Mover a un archivo de navigation...
    NavHost(
        navController = navController,
        startDestination = Screens.TasksScreen.route
    ) {
        composable(
            route = Screens.TasksScreen.route
        ) {
            Scaffold(
                topBar = topBar,
                bottomBar = bottomBar
            ) { innerPadding ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(bottom = innerPadding.calculateBottomPadding())) {
                    TasksView(state) { task ->
                        navController.navigate(Screens.TaskDetailScreen.withArgs(task))
                    }
                }
            }
        }
        composable(
            route = Screens.AddTaskScreen.route,
        ) {
            //EditTaskScreen(HiltViewModel<EditTaskViewModel>(), args = null, navController)
            EditTaskScreen(navController)
        }
        composable(
            route = Screens.TaskDetailScreen.route + Screens.TaskDetailScreen.argsDef,
            arguments = Screens.TaskDetailScreen.args,
        ) {
            TaskDetailScreen(it.arguments, navController) {
                navController.popBackStack()
            }
        }
        composable(
            route = Screens.EditTaskScreen.route + Screens.EditTaskScreen.argsDef,
            arguments = Screens.EditTaskScreen.args,
        ) {
            EditTaskScreen(navController, it.arguments)
            //{ navController.popBackStack() }
        }
    }
}
