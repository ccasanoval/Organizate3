package com.cesoft.organizate3.domain.model

import java.util.Date

data class Task(
    val id: Int,
    val name: String,
    val description: String = "",
    val dueDate: Date = Date(),
    val done: Boolean = false,
    val priority: Priority = Priority.LOW,
    val type: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val radius: Int=0,
    //TODO: Image
) {
    enum class Priority(val value: Int) {
        LOW(0), P1(1), P2(2), MID(3), P4(4), HIGH(5);
        companion object {
            private val VALUES = values()
            fun getByValue(value: Int) = VALUES.firstOrNull { it.value == value }
        }
    }

}