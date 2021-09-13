package com.cesoft.organizate3.ui.screen.taskadd

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cesoft.organizate3.data.Repository
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.usecase.AddTaskUseCase
import com.cesoft.organizate3.ui.composables.MapState
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Marker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class AddTaskViewModel(app: Application) : AndroidViewModel(app) {
    enum class Field {
        Name, Description, Type, DueDate, Done, Priority, LatLon, Radius
    }
    sealed class Intent {
        object Save : Intent()
        data class ChangeField(val field: Field, val value: Any?) : Intent()
    }

    private val repo = Repository(app.applicationContext)
    private val addTask = AddTaskUseCase(repo, Dispatchers.IO)

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
            _suggestion.value= repo.getTaskTypes()
        }
    }

    suspend fun sendIntent(intent: Intent) {
        when(intent) {
            is Intent.ChangeField -> {
                android.util.Log.e("AddTaskView", "sendIntent---$intent--------***------${intent.field}")

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
                val task = Task(0, name.value, description.value, dueDate.value, done.value, priority.value,
                    type.value, latLng.value.latitude, latLng.value.longitude, radius.value)
                android.util.Log.e("AddTaskView", "sendIntent---$intent--------***------ task=$task")
                addTask(task)
            }
        }
    }
}
