package com.cesoft.organizate3.ui.screen.taskadd

sealed class State {
    object Loading: State()
    object Editing: State()
    object Saved: State()
    object Error: State()
}
