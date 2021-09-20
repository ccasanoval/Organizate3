package com.cesoft.organizate3.ui.screen.taskedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesoft.organizate3.domain.UseCaseResult
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.usecase.AddTaskUseCase
import com.cesoft.organizate3.domain.usecase.GetTaskByIdUseCase
import com.cesoft.organizate3.domain.usecase.GetTaskTypesUseCase
import com.cesoft.organizate3.domain.usecase.UpdateTaskUseCase
import com.cesoft.organizate3.ui.composables.MapState
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTask: UpdateTaskUseCase,
    private val addTask: AddTaskUseCase,
    private val getTaskTypes: GetTaskTypesUseCase
) : ViewModel() {
    private var isNewTask = true
    private var task: Task? = null

    private val _state = MutableStateFlow<State>(State.Loading)
    val state: Flow<State> = _state

    //private val repo = Repository(app.applicationContext)
    //private val getTaskByIdUseCase = GetTaskByIdUseCase(repo, Dispatchers.IO)
    //private val updateTask = UpdateTaskUseCase(repo, Dispatchers.IO)
    //private val addTask = AddTaskUseCase(repo, Dispatchers.IO)
    //@Inject lateinit var getTaskByIdUseCase: GetTaskByIdUseCase
    //@Inject lateinit var updateTask: UpdateTaskUseCase
    //@Inject lateinit var addTask: AddTaskUseCase
    //@Inject lateinit var getTaskTypes: GetTaskTypesUseCase


    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name
    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description
    private val _type = MutableStateFlow("")
    val type: StateFlow<String> = _type
    private val _done = MutableStateFlow(false)
    val done: StateFlow<Boolean> = _done
    private val _priority = MutableStateFlow(Task.Priority.LOW)
    val priority: StateFlow<Task.Priority> = _priority
    private val _dueDate = MutableStateFlow(Date())
    val dueDate: StateFlow<Date> = _dueDate
    private val _latLng = MutableStateFlow(LatLng(0.0, 0.0))
    val latLng: StateFlow<LatLng> = _latLng
    private val _radius = MutableStateFlow(0)
    val radius: StateFlow<Int> = _radius

    val mapState = MapState(LatLng(0.0, 0.0), 8f, null)

    private val _suggestion = MutableStateFlow<List<String>>(listOf())
    val suggestions: StateFlow<List<String>> = _suggestion

    init {
        viewModelScope.launch(Dispatchers.IO) {
            when(val res=getTaskTypes(null)) {
                UseCaseResult.Loading -> {}
                is UseCaseResult.Error -> {}
                is UseCaseResult.Success -> {
                    _suggestion.value = res.data
                }
            }
        }
    }

    suspend fun sendIntent(intent: Intent) {
        when(intent) {
            is Intent.Init -> {
                intent.id?.let {
                    when(val res = getTaskByIdUseCase(intent.id)) {
                        UseCaseResult.Loading -> {
                            _state.emit(State.Loading)
                        }
                        is UseCaseResult.Error -> {
                            _state.emit(State.Error(isNewTask))
                        }
                        is UseCaseResult.Success -> {
                            task = res.data
                            task?.let {
                                isNewTask = false
                                _name.value = it.name
                                _description.value = it.description
                                _type.value = it.type
                                _done.value = it.done
                                _dueDate.value = it.dueDate
                                _priority.value = it.priority
                                _radius.value = it.radius
                                _latLng.value = LatLng(it.latitude, it.longitude)
                            }
                            _state.emit(State.Editing(name.value))
                        }
                    }
                } ?: run {
                    _state.emit(State.Editing(null))
                }
            }
            is Intent.ChangeField -> {
                when(intent.field) {
                    Field.Name -> _name.value = intent.value as String
                    Field.Description -> _description.value = intent.value as String
                    Field.Type -> _type.value = intent.value as String
                    Field.Done -> _done.value = intent.value as Boolean
                    Field.DueDate -> _dueDate.value = intent.value as Date
                    Field.LatLon -> _latLng.value = intent.value as LatLng
                    Field.Radius -> _radius.value = intent.value as Int
                    Field.Priority -> _priority.value = Task.Priority.getByValue((intent.value as Float).toInt()) ?: Task.Priority.LOW
                }
            }
            is Intent.Save -> {
                val res = if(isNewTask) {
                    val task = Task(0, name.value, description.value, dueDate.value, done.value, priority.value,
                        type.value, latLng.value.latitude, latLng.value.longitude, radius.value)
                    addTask(task)
                }
                else {
                    val task = Task(task!!.id, name.value, description.value, dueDate.value, done.value, priority.value,
                        type.value, latLng.value.latitude, latLng.value.longitude, radius.value)
                    updateTask(task)
                }
                when(res) {
                    UseCaseResult.Loading -> _state.emit(State.Loading)
                    is UseCaseResult.Error -> _state.emit(State.Error(isNewTask))
                    is UseCaseResult.Success -> _state.emit(State.Saved)
                }
            }
            Intent.Done -> _state.emit(State.Editing(name.value))
        }
    }
}