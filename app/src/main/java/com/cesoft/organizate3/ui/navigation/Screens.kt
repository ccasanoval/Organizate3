package com.cesoft.organizate3.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import com.cesoft.organizate3.R
import com.cesoft.organizate3.domain.model.Task

const val TASK_ID = "TASK_ID"

sealed class Screens(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector,
    val argsDef: String = "",
    val args: List<NamedNavArgument> = emptyList()
) {

    object TasksScreen : Screens("Tasks", R.string.tasks, Icons.Default.Task)
    object AddTaskScreen : Screens("AddTask", R.string.new_task, Icons.Default.AddTask)
    object TaskDetailScreen : Screens("TaskDetail", R.string.tasks, Icons.Default.TaskAlt,
        "/{$TASK_ID}",
        listOf(
            navArgument(TASK_ID) {
                nullable = false
                type = NavType.IntType
            },
        ))
}

fun Screens.TaskDetailScreen.withArgs(task: Task) = "$route/${task.id}"

fun NavBackStackEntry.getStringArg(key: String) =
    this.arguments?.getString(key).toString()
