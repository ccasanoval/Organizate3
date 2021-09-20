package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.repo.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetTaskTypesUseCase @Inject constructor(
    private val repo: TaskRepository,
    ioDispatcher: CoroutineDispatcher
) : SuspendUseCase<Any?, List<String>>(ioDispatcher) {
    override suspend fun execute(parameters: Any?): List<String> {
        return repo.getTaskTypes()
    }
}
