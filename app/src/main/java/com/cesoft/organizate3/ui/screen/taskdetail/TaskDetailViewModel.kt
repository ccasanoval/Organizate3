package com.cesoft.organizate3.ui.screen.taskdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cesoft.organizate3.data.Repository
import com.cesoft.organizate3.domain.UseCaseResult
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.usecase.DeleteTaskUseCase
import com.cesoft.organizate3.domain.usecase.GetTaskByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TaskDetailViewModel(app: Application) : AndroidViewModel(app) {
    val stateInit = State.Fetch(UseCaseResult.Loading)

    private var idTask = -1
    private val repo = Repository(app.applicationContext)
    private val getTask = GetTaskByIdUseCase(repo, Dispatchers.IO)
    private val deleteTask = DeleteTaskUseCase(repo, Dispatchers.IO)

    private val _state = MutableStateFlow<State>(stateInit)
    val state: Flow<State> = _state

    suspend fun sendIntent(intent: Intent) {
        when(intent) {
            is Intent.Init -> {
                idTask = intent.idTask
                val res = getTask(idTask)//repo.getTaskById(intent.idTask)
                android.util.Log.e("TaskDetVM", "sendIntent:Init----------------${intent.idTask} = $res")
                _state.emit(State.Fetch(res))
            }
            is Intent.Delete -> {
                val res = deleteTask(idTask)
                _state.emit(State.Delete(res))
            }
        }
    }
}