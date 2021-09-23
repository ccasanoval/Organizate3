package com.cesoft.organizate3.ui.screen.tasklist

sealed class Intent {
    object Init: Intent()
    object Search: Intent()
    data class FilterTaskType(val type: String): Intent()
    //data class ItemClick(val task: Task): Intent()
}
