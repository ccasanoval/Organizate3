package com.cesoft.organizate3.data

import android.content.Context
import androidx.room.Room
import com.cesoft.organizate3.data.local.TaskDatabase
import com.cesoft.organizate3.data.local.toDb
import com.cesoft.organizate3.data.local.toModel
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(applicationContext: Context): TaskRepository {

    private val db = Room.databaseBuilder(
        applicationContext,
        TaskDatabase::class.java, "organizate-tasks"
    ).build()

    // Select -------------------------------------------------------------------------------------

    override suspend fun getTasks(): Flow<List<Task>> {
        val dao = db.taskDao()
        return dao.getAll().toModel()
    }

    override suspend fun getTaskById(id: Int): Task? {
        val dao = db.taskDao()
        return dao.getById(id)?.toModel()
    }

    override suspend fun getTaskTypes(): List<String> {
        val dao = db.taskDao()
        return dao.getTaskTypes()
    }

    override suspend fun getTasksByType(type: String): Flow<List<Task>> {
        val dao = db.taskDao()
        return dao.getTasksByTypes(type).toModel()
    }

    // Insert -------------------------------------------------------------------------------------

    override suspend fun saveTask(task: Task) {
        val dao = db.taskDao()
        dao.insert(task.toDb())
    }

    // Update -------------------------------------------------------------------------------------

    override suspend fun updateTask(task: Task) {
        val dao = db.taskDao()
        dao.update(task.toDb())
    }

    // Delete -------------------------------------------------------------------------------------

    override suspend fun deleteAllTasks() {
        val dao = db.taskDao()
        dao.deleteAll()
    }

    override suspend fun deleteTask(idTask: Int) {
        val dao = db.taskDao()
        dao.delete(idTask)
    }
}
