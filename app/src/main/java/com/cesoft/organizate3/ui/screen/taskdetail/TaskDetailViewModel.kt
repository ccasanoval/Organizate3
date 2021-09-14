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
    sealed class Intent {
        data class Init(val idTask: Int): Intent()
        object Delete: Intent()
    }
    sealed class State {
        data class Fetch(val res: UseCaseResult<Task?>): State()
        data class Delete(val res: UseCaseResult<Unit>): State()
    }
    val fetchLoadingState = State.Fetch(UseCaseResult.Loading)

    private var idTask = -1
    private val repo = Repository(app.applicationContext)
    private val getTask = GetTaskByIdUseCase(repo, Dispatchers.IO)
    private val deleteTask = DeleteTaskUseCase(repo, Dispatchers.IO)

    private val _state = MutableStateFlow<State>(fetchLoadingState)
    val state: Flow<State> = _state

    fun sendIntent(intent: Intent) {
        when(intent) {
            is Intent.Init -> {
                viewModelScope.launch(Dispatchers.IO) {
                    idTask = intent.idTask
                    val res = getTask(idTask)//repo.getTaskById(intent.idTask)
                    android.util.Log.e("TaskDetVM", "sendIntent:Init----------------${intent.idTask} = $res")
                    _state.emit(State.Fetch(res))
                }
            }
            is Intent.Delete -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val res = deleteTask(idTask)
                    _state.emit(State.Delete(res))
                }
            }
        }
    }
}