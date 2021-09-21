package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.repo.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteAllTasksUseCase @Inject constructor(
    private val repo: TaskRepository,
    ioDispatcher: CoroutineDispatcher
) : SuspendUseCase<Any?, Unit>(ioDispatcher) {
    override suspend fun execute(parameters: Any?) = repo.deleteAllTasks()
}
