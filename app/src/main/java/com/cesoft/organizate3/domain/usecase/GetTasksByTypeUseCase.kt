package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.repo.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksByTypeUseCase @Inject constructor(
        private val repo: TaskRepository,
        ioDispatcher: CoroutineDispatcher
    ) : SuspendUseCase<String, Flow<List<Task>>>(ioDispatcher) {
        override suspend fun execute(parameters: String): Flow<List<Task>> {
            return repo.getTasksByType(parameters)
        }
    }
