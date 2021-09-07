package com.cesoft.organizate3.ui.screen.tasklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.ui.navigation.Screens
import com.cesoft.organizate3.ui.navigation.getStringArg
import com.cesoft.organizate3.ui.screen.MainBottomNavigation
import com.cesoft.organizate3.ui.screen.TaskDetailScreen
import com.cesoft.organizate3.ui.screen.taskadd.AddTaskScreen

private const val TASK_ID = "TASK_ID"
private const val TASK_NAME = "TASK_NAME"

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = viewModel()
    val navController = rememberNavController()

    val bottomBar: @Composable () -> Unit =
        { MainBottomNavigation(navController) }
    val topBar: @Composable () -> Unit =
        { MainToolbar(viewModel) }

    NavHost(
        navController = navController,
        startDestination = Screens.TasksScreen.route
    ) {
        composable(Screens.TasksScreen.route) {
            Scaffold(
                topBar = topBar,
                bottomBar = bottomBar
            ) {
                TasksView(viewModel, navController)
            }
        }
        composable(
            route = Screens.TaskDetailScreen.route + "?$TASK_ID={id}&$TASK_NAME={name}",
            arguments = listOf(
                navArgument(TASK_ID) { nullable = true },
                navArgument(TASK_NAME) { nullable = true }
            ),
        ) {
            TaskDetailScreen(
                id = it.getStringArg("id"),
                name = it.getStringArg("name"),
            ) {
                navController.popBackStack()
            }
        }
        composable(Screens.AddTaskScreen.route) {
            AddTaskScreen(navController)
        }
    }
}

@Composable
fun TasksView(viewModel: MainViewModel, navController: NavHostController) {
    TasksList(viewModel) { task ->
        navController.navigate(
            Screens.TaskDetailScreen.withArgs(
                args = mapOf(
                    TASK_ID to task.id.toString(),
                    TASK_NAME to task.name,
                )
            )
        )
    }
}

@Composable
fun TasksList(viewModel: MainViewModel, onTaskClick: (task: Task) -> Unit) {
    val tasks = viewModel.tasks.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        items(tasks) { task ->
            task?.let {
                TasksListRowView(it) { taskClicked ->
                    onTaskClick(taskClicked)
                }
            }
        }
    }
}

@Composable
fun TasksListRowView(
    task: Task,
    onClickListener: (task: Task) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onClickListener(task)
            })
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                task.name,
                style = MaterialTheme.typography.h6, maxLines = 1, overflow = TextOverflow.Ellipsis
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    task.description,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
    Divider(modifier = Modifier.padding(horizontal = 6.dp))
}