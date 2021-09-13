package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.repo.TaskRepository
import com.cesoft.organizate3.domain.model.Task
import kotlinx.coroutines.CoroutineDispatcher

class GetTasksUseCase(
    private val repo: TaskRepository,
    ioDispatcher: CoroutineDispatcher
) : SuspendUseCase<Any?, List<Task>>(ioDispatcher) {
    override suspend fun execute(parameters: Any?): List<Task> = repo.getTasks()
}

/*
class GetTasksUseCase(
    private val repository: TaskRepository,
    ioDispatcher: CoroutineDispatcher
) : UseCase<Void, Flow<UseCaseResult<List<Task>>>>() {

    override fun execute(param: Void): Flow<UseCaseResult<List<Task>>> {}
    override fun execute(): Flow<UseCaseResult<List<Task>>> =
        flow {
            try {
                val tasks = repository.getTasks()
                emit(UseCaseResult.Success(tasks))
            } catch(e: Exception) {
                emit(UseCaseResult.Error(e))
            }
        }
}
*/