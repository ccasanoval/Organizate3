package com.cesoft.organizate3.ui.screen.tasklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cesoft.organizate3.domain.UseCaseResult
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.ui.composables.LoadingCompo
import com.cesoft.organizate3.ui.navigation.Screens
import com.cesoft.organizate3.ui.navigation.withArgs
import com.cesoft.organizate3.ui.screen.MainBottomNavigation
import com.cesoft.organizate3.ui.screen.taskadd.AddTaskScreen
import com.cesoft.organizate3.ui.screen.taskdetail.TaskDetailScreen
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun MainScreen() {
    android.util.Log.e("MainV", "MainScreen--------------------------------------------------------- ")

    val viewModel: MainViewModel = viewModel()
    LaunchedEffect(true) {
        viewModel.sendIntent(Intent.Init)
    }

    val navController = rememberNavController()
    val bottomBar: @Composable () -> Unit =
        { MainBottomNavigation(navController) }
    val topBar: @Composable () -> Unit =
        { MainToolbar(viewModel) }
    val onSelectedTask: (Task) -> Unit = {
        navController.navigate(Screens.TaskDetailScreen.withArgs(it))
    }

    val state = viewModel.state.collectAsState(State.Loading).value

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
            ) {
                TasksView(state) { task -> onSelectedTask(task) }
            }
        }
        composable(
            route = Screens.AddTaskScreen.route,
        ) {
            AddTaskScreen(navController)
        }
        composable(
            route = Screens.TaskDetailScreen.route + Screens.TaskDetailScreen.argsDef,
            arguments = Screens.TaskDetailScreen.args,
        ) {
            TaskDetailScreen(it.arguments) {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun TasksView(state: State, onSelectedTask: (task: Task) -> Unit) {
    when(state) {
        State.Loading -> {
            android.util.Log.e("MainV", "Loading --------------------")
            LoadingCompo()
        }
        is State.Error -> {
            android.util.Log.e("MainV", "Error --------------------${state}")
        }
        is State.Data -> {
            //collectAsLazyPagingItems
            val tasksFlow = state.tasks
            val tasks = tasksFlow.collectAsState(null).value
            android.util.Log.e("MainV", "Success -------------------- $tasks")
            tasks?.let {
                TasksList(tasks) { task -> onSelectedTask(task) }
            }
        }
    }
}

@Composable
fun TasksList(tasks: List<Task>, onTaskClick: (task: Task) -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        items(tasks) { task ->
            TasksListRowView(task) { taskClicked ->
                onTaskClick(taskClicked)
            }
        }
    }
}

@Composable
fun TasksListRowView(
    task: Task,
    onClickListener: (task: Task) -> Unit
) {
    val taskName = if(task.name.isEmpty()) "?" else task.name
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
                taskName,
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
    Divider(modifier = Modifier.padding(horizontal = 6.dp))//TODO:Dark mode color
}