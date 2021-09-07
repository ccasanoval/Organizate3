package com.cesoft.organizate3.domain.usecase

import com.cesoft.organizate3.domain.UseCaseResult
import com.cesoft.organizate3.domain.repo.TaskRepository
import com.cesoft.organizate3.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTasksUseCase(
    private val repository: TaskRepository,
) : UseCase<Unit, Flow<UseCaseResult<List<Task>>>>() {

    override fun execute(parameters: Unit): Flow<UseCaseResult<List<Task>>> =
        flow {
            try {
                val tasks = repository.getTasks()
                emit(UseCaseResult.Success(tasks))
            } catch(e: Exception) {
                emit(UseCaseResult.Error(e))
            }
        }
}
