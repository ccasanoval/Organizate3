package com.cesoft.organizate3.ui.screen.taskdetail

sealed class Intent {
    data class Init(val idTask: Int): Intent()
    object Delete: Intent()
}
