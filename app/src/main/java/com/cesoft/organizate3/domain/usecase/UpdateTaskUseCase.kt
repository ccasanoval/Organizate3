package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.repo.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher

class UpdateTaskUseCase(
    private val repo: TaskRepository,
    ioDispatcher: CoroutineDispatcher
) : SuspendUseCase<Task, Unit>(ioDispatcher) {
    override suspend fun execute(parameters: Task) = repo.updateTask(parameters)
}
