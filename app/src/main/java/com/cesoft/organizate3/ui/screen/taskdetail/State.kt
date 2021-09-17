package com.cesoft.organizate3.ui.screen.taskdetail

import com.cesoft.organizate3.domain.UseCaseResult
import com.cesoft.organizate3.domain.model.Task

sealed class State {
    data class Fetch(val res: UseCaseResult<Task?>): State()
    data class Delete(val res: UseCaseResult<Unit>): State()
    object Done: State()
}
