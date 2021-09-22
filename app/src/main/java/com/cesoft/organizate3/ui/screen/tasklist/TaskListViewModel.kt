package com.cesoft.organizate3.ui.screen.tasklist

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cesoft.organizate3.domain.UseCaseResult
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.usecase.AddTaskUseCase
import com.cesoft.organizate3.domain.usecase.DeleteAllTasksUseCase
import com.cesoft.organizate3.domain.usecase.GetTaskTypesUseCase
import com.cesoft.organizate3.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTasksUC: GetTasksUseCase,
    private val addTaskUC: AddTaskUseCase,
    private val deleteAllTasksUC: DeleteAllTasksUseCase,
    private val getTaskTypesUC: GetTaskTypesUseCase
) : ViewModel() {

    private val list0 = listOf(
        Task(0,"Task 0", "The #0 task", java.util.Date(), true, Task.Priority.LOW, "Lmn", 40.50, -3.42, 100),
        Task(1,"Task 1", "The #1 task", java.util.Date(), false, Task.Priority.HIGH, "Abc",40.51, -3.44, 100),
        Task(2,"Task 2", "The #2 task", java.util.Date(), false, Task.Priority.MID, "Zyx",40.55, -3.47, 100),
        Task(3,"Task 3", "The #3 task", java.util.Date(), true, Task.Priority.LOW, "Abc",40.59, -3.40, 100)
    )

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state
    private val _suggestion = MutableStateFlow<List<String>>(listOf())
    val suggestions: StateFlow<List<String>> = _suggestion

    /*init {
        viewModelScope.launch(Dispatchers.IO) {
            when(val res=getTaskTypes(null)) {
                UseCaseResult.Loading -> {}
                is UseCaseResult.Error -> {}
                is UseCaseResult.Success -> {
                    _suggestion.value = res.data
                }
            }
        }
    }*/

    private suspend fun getTaskTypes() {
        when(val res = getTaskTypesUC(null)) {
            UseCaseResult.Loading -> {}
            is UseCaseResult.Error -> {}
            is UseCaseResult.Success -> {
                _suggestion.value = res.data
            }
        }
    }

    private suspend fun getTasks() {
        when(val res = getTasksUC(null)) {
            UseCaseResult.Loading -> _state.emit(State.Loading)
            is UseCaseResult.Error -> _state.emit(State.Error(res.exception))
            is UseCaseResult.Success -> _state.emit(State.Data(res.data))
        }
    }

    private suspend fun testInit() {
        deleteAllTasksUC(null)
        for(task in list0) {
            addTaskUC(task)
        }
    }

    suspend fun sendIntent(intent: Intent) {
        Log.e(TAG, "sendIntent---$intent--------------------------- ")
        when(intent) {
            Intent.Init -> {
                if(false) testInit()
                getTaskTypes()
                getTasks()
            }
            Intent.Search -> {
                //_tasks.emit(PagingData.from(list1))
            }
            //is Intent.ItemClick -> Log.e(TAG, "sendIntent---$intent--------***------ ${intent.task}")
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}
