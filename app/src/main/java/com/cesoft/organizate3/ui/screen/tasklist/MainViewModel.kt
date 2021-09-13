package com.cesoft.organizate3.ui.screen.tasklist

import android.app.Application
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
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    sealed class Intent {
        object Init: Intent()
        object Search: Intent()
        data class ItemClick(val task: Task): Intent()
    }

    private val list0 = listOf(
        Task(0,"Task 0", "The #0 task", java.util.Date(), true, Task.Priority.LOW, "Lmn", 40.50, -3.45, 100),
        Task(1,"Task 1", "The #1 task", java.util.Date(), false, Task.Priority.HIGH, "Abc"),
        Task(2,"Task 2", "The #2 task", java.util.Date(), false, Task.Priority.MID, "Zyx"),
        Task(3,"Task 3", "The #3 task", java.util.Date(), true, Task.Priority.LOW, "Abc")
    )
/*    private val list1 = listOf(
        Task(0,"Task 1", "The #1 task", Date(), false),
        Task(1,"Task 2", "The #2 task", Date(), false),
        Task(2,"Task 3", "The #3 task", Date(), true),
        Task(3,"Task 4", "The #4 task", Date(), true),
        Task(4,"Task 5", "The #5 task", Date(), false),
        Task(5,"Task 11", "The #1 task", Date(), false),
        Task(6,"Task 12", "The #2 task", Date(), false),
        Task(7,"Task 13", "The #3 task", Date(), true),
        Task(8,"Task 14", "The #4 task", Date(), true),
        Task(9,"Task 15", "The #5 task", Date(), false),
        Task(10,"Task 21", "The #1 task", Date(), false),
        Task(11,"Task 22", "The #2 task", Date(), false),
        Task(12,"Task 23", "The #3 task", Date(), true),
        Task(13,"Task 24", "The #4 task", Date(), true),
        Task(14,"Task 25", "The #5 task", Date(), false),
    )*/

    private val repo = Repository(app.applicationContext)
    private val getTask = GetTasksUseCase(repo, Dispatchers.IO)
    private val addTask = AddTaskUseCase(repo, Dispatchers.IO)

    private val _state = MutableStateFlow<UseCaseResult<List<Task>>>(UseCaseResult.Loading)
    val state: Flow<UseCaseResult<List<Task>>> = _state

    fun sendIntent(intent: Intent) {
        when(intent) {
            is Intent.Init -> {

                viewModelScope.launch(Dispatchers.IO) {
                    if(false) {
                        repo.clean()
                        for(task in list0) {
                            addTask(task)
                        }
                        android.util.Log.e(TAG, "sendIntent-1--$intent--------------------------- ")
                    }
                    val res = getTask(null)
                    _state.emit(res)
                    android.util.Log.e(TAG, "sendIntent-1--------------------------- $res ")
                }
                //TODO: Use UseCases, remove repo !
            }
            is Intent.Search -> {
                android.util.Log.e(TAG, "sendIntent---$intent--------------------------- ")
                //_tasks.emit(PagingData.from(list1))
            }
            is Intent.ItemClick -> {
                android.util.Log.e(TAG, "sendIntent---$intent--------***------ ${intent.task}")
            }
        }
    }

    companion object {
        private const val TAG = "MainVM"
    }
}