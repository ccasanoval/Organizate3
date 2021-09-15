package com.cesoft.organizate3.data

import android.content.Context
import androidx.room.Room
import com.cesoft.organizate3.data.local.TaskDatabase
import com.cesoft.organizate3.data.local.toDb
import com.cesoft.organizate3.data.local.toModel
import com.cesoft.organizate3.domain.model.Task
import com.cesoft.organizate3.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow

class Repository(applicationContext: Context): TaskRepository {

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

    // Insert -------------------------------------------------------------------------------------

    override suspend fun saveTask(task: Task) {
        val dao = db.taskDao()
        dao.insert(task.toDb())
    }

    // Delete -------------------------------------------------------------------------------------

    override suspend fun clean() {
        val dao = db.taskDao()
        dao.deleteAll()
    }

    //override suspend fun deleteTask(task: Task) {
    //    val dao = db.taskDao()
    //    dao.delete(task.toDb())
    //}
    override suspend fun deleteTask(idTask: Int) {
        val dao = db.taskDao()
        dao.delete(idTask)
    }
}
