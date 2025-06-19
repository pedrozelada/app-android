package com.example.schedulemanagerapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assignments")
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val courseCode: String,
    val description: String,
    val dueDate: String,
    val completed: Boolean = false
)
