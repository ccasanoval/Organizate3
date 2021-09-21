package com.cesoft.organizate3.ui.screen.tasklist

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cesoft.organizate3.domain.UseCaseResult
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.usecase.AddTaskUseCase
import com.cesoft.organizate3.domain.usecase.DeleteAllTasksUseCase
import com.cesoft.organizate3.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTask: GetTasksUseCase,
    private val addTask: AddTaskUseCase,
    private val deleteAllTasks: DeleteAllTasksUseCase
) : ViewModel() {

    private val list0 = listOf(
        Task(0,"Task 0", "The #0 task", java.util.Date(), true, Task.Priority.LOW, "Lmn", 40.50, -3.42, 100),
        Task(1,"Task 1", "The #1 task", java.util.Date(), false, Task.Priority.HIGH, "Abc",40.51, -3.44, 100),
        Task(2,"Task 2", "The #2 task", java.util.Date(), false, Task.Priority.MID, "Zyx",40.55, -3.47, 100),
        Task(3,"Task 3", "The #3 task", java.util.Date(), true, Task.Priority.LOW, "Abc",40.59, -3.40, 100)
    )

    //var state: State = State.Loading
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    suspend fun sendIntent(intent: Intent) {
        when(intent) {
            Intent.Init -> {
                if(false) {
                    //repo.clean()
                    deleteAllTasks(null)
                    for(task in list0) {
                        addTask(task)
                    }
                    Log.e(TAG, "sendIntent-1--$intent--------------------------- ")
                }
                val res = getTask(null)
                when(res) {
                    UseCaseResult.Loading -> _state.emit(State.Loading)//state = State.Loading//
                    is UseCaseResult.Error -> _state.emit(State.Error(res.exception))//state = State.Error(res.exception)//
                    is UseCaseResult.Success -> {
                        //res.data.collect {
                        //    _state.emit(State.Data(it))
                        //}
                        _state.emit(State.Data(res.data))
                        //state = State.Data(res.data)
                    }
                }
                //_state.emit(res)
                Log.e(TAG, "sendIntent-1---------------------------res $res")
            }
            Intent.Search -> {
                Log.e(TAG, "sendIntent---$intent--------------------------- ")
                //_tasks.emit(PagingData.from(list1))
            }
            is Intent.ItemClick -> {
                Log.e(TAG, "sendIntent---$intent--------***------ ${intent.task}")
            }
        }
    }

    companion object {
        private const val TAG = "MainVM"
    }
}
