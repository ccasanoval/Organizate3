package com.cesoft.organizate3.data.local

import com.cesoft.organizate3.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

fun Flow<List<TaskEntity>>.toModel() = this.map { taskEntity ->  taskEntity.toModel() }

fun TaskEntity.toModel() =
    Task(
        this.id,
        this.name ?: "",
        this.description ?: "",
        this.dueDate ?: Date(),
        this.done,
        Task.Priority.getByValue(this.priority) ?: Task.Priority.LOW,
        this.type,
        this.latitude,
        this.longitude,
        this.radius
    )

fun List<TaskEntity>.toModel() =
    this.map { taskEntity ->  taskEntity.toModel() }

fun Task.toDb() =
    TaskEntity(
        this.id,
        this.name,
        this.description,
        this.dueDate,
        this.done,
        this.priority.value,
        this.type,
        this.latitude,
        this.longitude,
        this.radius
    )