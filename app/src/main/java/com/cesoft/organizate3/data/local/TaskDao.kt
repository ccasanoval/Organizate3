package com.cesoft.organizate3.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    /// QUERY -----------------------------------------------------------------

    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<TaskEntity>>

    //@Query("SELECT * FROM tasks WHERE id IN (:ids)")
    //fun getAllByIds(ids: IntArray): List<TaskEntity>

    //@Query("SELECT * FROM tasks WHERE name LIKE :name LIMIT 1")
    //fun getByName(name: String): TaskEntity

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getById(id: Int): TaskEntity?

    @Query("SELECT DISTINCT(type) FROM tasks")
    fun getTaskTypes(): List<String>


    /// INSERT ----------------------------------------------------------------

    //@Insert
    //fun insertAll(vararg tasks: TaskEntity)

    @Insert
    fun insert(task: TaskEntity)


    /// UPDATE ----------------------------------------------------------------

    @Update
    fun update(task: TaskEntity)


    /// DELETE ----------------------------------------------------------------

    //@Delete
    //fun delete(task: TaskEntity)
    @Query("DELETE FROM tasks WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM tasks")
    fun deleteAll()
}