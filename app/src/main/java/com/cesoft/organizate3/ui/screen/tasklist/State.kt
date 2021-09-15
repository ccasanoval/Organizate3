package com.cesoft.organizate3.ui.screen.tasklist

import com.cesoft.organizate3.domain.model.Task
import kotlinx.coroutines.flow.Flow

sealed class State {
    object Loading: State()
    data class Error(val exception: Exception): State()//object Error: State()
    data class Data(val tasks: Flow<List<Task>>): State()
}
