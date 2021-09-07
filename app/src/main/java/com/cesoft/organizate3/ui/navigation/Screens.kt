package com.cesoft.organizate3.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import com.cesoft.organizate3.R

sealed class Screens(val route: String, val label: Int, val icon: ImageVector) {
    object TasksScreen : Screens("Tasks", R.string.tasks, Icons.Default.Task)
    object TaskDetailScreen : Screens("TaskDetail", R.string.tasks, Icons.Default.TaskAlt)
    object AddTaskScreen : Screens("AddTask", R.string.new_task, Icons.Default.AddTask)

    fun withArgs(args: Map<String, String>): String {
        return buildString {
            append(route)
            append("?")
            val iterator = args.iterator()
            while (iterator.hasNext()) {
                val map = iterator.next()
                append(map.key + "=" + map.value)
                if (iterator.hasNext())
                    append("&")
            }
        }
    }
}

fun NavBackStackEntry.getStringArg(key: String) =
    this.arguments?.getString(key).toString()