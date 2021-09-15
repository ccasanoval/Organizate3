package com.cesoft.organizate3.ui.screen.tasklist

import com.cesoft.organizate3.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

sealed class State {
    object Loading: State()
    object Error: State()//data class Error(val exception: Exception): State()
    data class Data(val tasks: Flow<List<Task>>): State()
}
