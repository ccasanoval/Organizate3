package com.cesoft.organizate3.ui.screen.taskdetail

import androidx.lifecycle.ViewModel
import com.cesoft.organizate3.domain.UseCaseResult
import com.cesoft.organizate3.domain.usecase.DeleteTaskUseCase
import com.cesoft.organizate3.domain.usecase.GetTaskByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val getTaskById: GetTaskByIdUseCase,
    private val deleteTask: DeleteTaskUseCase
) : ViewModel() {
    val stateInit = State.Fetch(UseCaseResult.Loading)

    private var idTask = -1
    private val _state = MutableStateFlow<State>(stateInit)
    val state: Flow<State> = _state

    suspend fun sendIntent(intent: Intent) {
        when(intent) {
            is Intent.Init -> {
                idTask = intent.idTask
                val res = getTaskById(idTask)
                _state.emit(State.Fetch(res))
            }
            Intent.Delete -> {
                val res = deleteTask(idTask)
                _state.emit(State.Delete(res))
            }
            Intent.Done -> {
                _state.emit(State.Done)
            }
        }
    }
}