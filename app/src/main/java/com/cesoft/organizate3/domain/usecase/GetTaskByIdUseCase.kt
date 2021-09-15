package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.repo.TaskRepository
import com.cesoft.organizate3.domain.model.Task
import kotlinx.coroutines.CoroutineDispatcher

class GetTaskByIdUseCase(
    private val repo: TaskRepository,
    ioDispatcher: CoroutineDispatcher
) : SuspendUseCase<Int, Task?>(ioDispatcher) {
    override suspend fun execute(parameters: Int): Task? = repo.getTaskById(parameters)
}
