package com.cesoft.organizate3.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.runtime.Composable
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
    companion object {
        private const val argsDef = "/{$TASK_ID}"
        private val args = listOf(
            navArgument(TASK_ID) {
                nullable = false
                type = NavType.IntType
            },
        )
    }

    object TasksScreen : Screens("Tasks", R.string.tasks, Icons.Default.Task)
    object AddTaskScreen : Screens("AddTask", R.string.new_task, Icons.Default.AddTask)
    object EditTaskScreen : Screens("TaskEdit", R.string.task, Icons.Default.TaskAlt, argsDef, args)
    object TaskDetailScreen : Screens("TaskDetail", R.string.task, Icons.Default.TaskAlt, argsDef,  args)
}

fun Screens.withArgs(task: Task) = "$route/${task.id}"
//
//fun NavBackStackEntry.getStringArg(key: String) =
//    this.arguments?.getString(key).toString()
