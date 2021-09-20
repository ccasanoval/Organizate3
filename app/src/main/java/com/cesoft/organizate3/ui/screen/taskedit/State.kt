package com.cesoft.organizate3.ui.screen.taskedit

sealed class State {
    object Loading: State()
    data class Editing(val taskName: String?): State()
    object Saved: State()
    data class Error(val isNewTask: Boolean): State()
}
