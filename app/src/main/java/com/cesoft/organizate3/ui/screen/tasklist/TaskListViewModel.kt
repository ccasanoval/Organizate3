package com.cesoft.organizate3.ui.screen.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesoft.organizate3.domain.UseCaseResult
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.usecase.AddTaskUseCase
import com.cesoft.organizate3.domain.usecase.DeleteAllTasksUseCase
import com.cesoft.organizate3.domain.usecase.GetTaskTypesFlowUseCase
import com.cesoft.organizate3.domain.usecase.GetTasksByTypeUseCase
import com.cesoft.organizate3.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTasksUC: GetTasksUseCase,
    private val addTaskUC: AddTaskUseCase,
    private val deleteAllTasksUC: DeleteAllTasksUseCase,
    private val getTaskTypesUC: GetTaskTypesFlowUseCase,
    private val getTasksByTypeUC: GetTasksByTypeUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state
    var taskTypeSuggestions: Flow<List<String>> = emptyFlow()
        private set
    var type: String = ALL
        private set

    init {
        viewModelScope.launch {
            if(false) testInit()
            getTaskTypes()
            getTasks(getTasksUC(null))
        }
    }

    private suspend fun getTaskTypes() {
        when(val res = getTaskTypesUC(null)) {
            UseCaseResult.Loading -> {}
            is UseCaseResult.Error -> {}
            is UseCaseResult.Success -> {
                taskTypeSuggestions = res.data.map { list ->
                    val m = mutableListOf<String>()
                    m.add(ALL)
                    m.addAll(list.filter { it.isNotEmpty() })
                    m.toList()
                }
            }
        }
    }

    private suspend fun getTasks(res: UseCaseResult<Flow<List<Task>>>) {
        when(res) {
            UseCaseResult.Loading -> _state.emit(State.Loading)
            is UseCaseResult.Error -> _state.emit(State.Error(res.exception))
            is UseCaseResult.Success -> _state.emit(State.Data(res.data))
        }
    }

    suspend fun sendIntent(intent: Intent) {
        when(intent) {
            Intent.Search -> {
                //_tasks.emit(PagingData.from(list1))
            }
            is Intent.FilterTaskType -> {
                type = intent.type
                if(intent.type == ALL)
                    getTasks(getTasksUC(null))
                else
                    getTasks(getTasksByTypeUC(intent.type))
            }
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName
        private const val ALL = ""//"-"
    }


    private suspend fun testInit() {
        deleteAllTasksUC(null)//TODO: Clean the null parameter?
        val list0 = listOf(
            Task(0,"Task 0", "The #0 task", java.util.Date(), true, Task.Priority.LOW, "Lmn", 40.50, -3.42, 100),
            Task(1,"Task 1", "The #1 task", java.util.Date(), false, Task.Priority.HIGH, "Abc",40.51, -3.44, 100),
            Task(2,"Task 2", "The #2 task", java.util.Date(), false, Task.Priority.MID, "Zyx",40.55, -3.47, 100),
            Task(3,"Task 3", "The #3 task", java.util.Date(), true, Task.Priority.LOW, "Abc",40.59, -3.40, 100)
        )
        for(task in list0) {
            addTaskUC(task)
        }
    }
}
