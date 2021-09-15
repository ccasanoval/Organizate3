package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.repo.TaskRepository
import com.cesoft.organizate3.domain.model.Task
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(
    private val repo: TaskRepository,
    ioDispatcher: CoroutineDispatcher
) : SuspendUseCase<Any?, Flow<List<Task>>>(ioDispatcher) {
    override suspend fun execute(parameters: Any?): Flow<List<Task>> {
        return repo.getTasks()
    }
}
