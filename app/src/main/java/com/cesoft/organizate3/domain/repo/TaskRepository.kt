package com.cesoft.organizate3.domain.repo

import com.cesoft.organizate3.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    // Select -------------------------------------------------------------------------------------
    suspend fun getTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: Int): Task?
    suspend fun getTaskTypes(): List<String>
    // Insert -------------------------------------------------------------------------------------
    suspend fun saveTask(task: Task)
    // Delete -------------------------------------------------------------------------------------
    //suspend fun deleteTask(task: Task)
    suspend fun deleteTask(idTask: Int)
    suspend fun clean()

}
