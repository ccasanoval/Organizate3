package com.cesoft.organizate3.ui.screen.tasklist

import com.cesoft.organizate3.domain.model.Task

sealed class Intent {
    object Init: Intent()
    object Search: Intent()
    //data class ItemClick(val task: Task): Intent()
}
