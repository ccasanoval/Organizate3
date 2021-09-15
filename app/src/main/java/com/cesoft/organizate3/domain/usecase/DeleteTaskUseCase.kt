package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.repo.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher

class DeleteTaskUseCase(
    private val repo: TaskRepository,
    ioDispatcher: CoroutineDispatcher
) : SuspendUseCase<Int, Unit>(ioDispatcher) {
    override suspend fun execute(parameters: Int) = repo.deleteTask(parameters)
}
