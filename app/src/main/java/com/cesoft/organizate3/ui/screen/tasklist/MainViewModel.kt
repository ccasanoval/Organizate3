package com.cesoft.organizate3.ui.screen.tasklist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cesoft.organizate3.data.Repository
import com.cesoft.organizate3.domain.UseCaseResult
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.usecase.AddTaskUseCase
import com.cesoft.organizate3.domain.usecase.GetTasksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val list0 = listOf(
        Task(0,"Task 0", "The #0 task", java.util.Date(), true, Task.Priority.LOW, "Lmn", 40.50, -3.42, 100),
        Task(1,"Task 1", "The #1 task", java.util.Date(), false, Task.Priority.HIGH, "Abc",40.51, -3.44, 100),
        Task(2,"Task 2", "The #2 task", java.util.Date(), false, Task.Priority.MID, "Zyx",40.55, -3.47, 100),
        Task(3,"Task 3", "The #3 task", java.util.Date(), true, Task.Priority.LOW, "Abc",40.59, -3.40, 100)
    )

    private val repo = Repository(app.applicationContext)
    private val getTask = GetTasksUseCase(repo, Dispatchers.IO)//TODO: DI
    private val addTask = AddTaskUseCase(repo, Dispatchers.IO)

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> = _state

    //init {
    //    viewModelScope.launch {
    //        sendIntent(Intent.Init)
    //    }
    //}

    suspend fun sendIntent(intent: Intent) {
        when(intent) {
            Intent.Init -> {
                if(false) {
                    repo.clean()
                    for(task in list0) {
                        addTask(task)
                    }
                    Log.e(TAG, "sendIntent-1--$intent--------------------------- ")
                }
                val res = getTask(null)
                when(res) {
                    UseCaseResult.Loading -> _state.emit(State.Loading)
                    is UseCaseResult.Error -> _state.emit(State.Error)
                    is UseCaseResult.Success -> _state.emit(State.Data(res.data))
                }
                //_state.emit(res)
                Log.e(TAG, "sendIntent-1---------------------------res $res ")
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
