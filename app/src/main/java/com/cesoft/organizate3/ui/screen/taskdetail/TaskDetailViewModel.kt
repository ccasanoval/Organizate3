package com.cesoft.organizate3.ui.screen.taskdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cesoft.organizate3.data.Repository
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.usecase.AddTaskUseCase
import com.google.android.libraries.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class TaskDetailViewModel(app: Application) : AndroidViewModel(app) {
    sealed class Intent {
        data class Init(val idTask: Int): Intent()
    }

    private val repo = Repository(app.applicationContext)

    private val _task = MutableStateFlow(Task())
    val task: Flow<Task> = _task

    suspend fun sendIntent(intent: Intent) {
        when(intent) {
            is Intent.Init -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val task = repo.getTaskById(intent.idTask)
                    android.util.Log.e("TaskDetVM", "sendIntent:Init----------------${intent.idTask} = $task")
                    task?.let {
                        _task.emit(it)
                    }
                }
            }
        }
    }
}