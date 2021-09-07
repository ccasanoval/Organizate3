package com.cesoft.organizate3.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//https://developer.android.com/training/data-storage/room/defining-data
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "due_date") val dueDate: Date?,
    val done: Boolean,
    val priority: Int,
    val type: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Int,
)