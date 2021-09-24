package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.repo.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskTypesFlowUseCase @Inject constructor(
    private val repo: TaskRepository,
    ioDispatcher: CoroutineDispatcher
) : SuspendUseCase<Any?, Flow<List<String>>>(ioDispatcher) {
    override suspend fun execute(parameters: Any?): Flow<List<String>> {
        return repo.getTaskTypesFlow()
    }
}
