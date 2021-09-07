package com.cesoft.organizate3.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {

    /// QUERY -----------------------------------------------------------------
    @Query("SELECT * FROM tasks")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id IN (:ids)")
    fun getAllByIds(ids: IntArray): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE name LIKE :name LIMIT 1")
    fun getByName(name: String): TaskEntity

    /// INSERT ----------------------------------------------------------------
    @Insert
    fun insertAll(vararg tasks: TaskEntity)

    @Insert
    fun insert(task: TaskEntity)

    /// DELETE ----------------------------------------------------------------
    @Delete
    fun delete(user: TaskEntity)

    @Query("DELETE FROM tasks")
    fun deleteAll()
}