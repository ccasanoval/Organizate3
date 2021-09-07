package com.cesoft.organizate3.domain.repo

import com.cesoft.organizate3.domain.model.Task

interface TaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun saveTask(task: Task)
    suspend fun clean()
}
