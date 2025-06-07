package com.example.schedulemanagerapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "assignments")
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val courseCode: String,
    val description: String,
    val dueDate: LocalDate,
    val completed: Boolean = false
)
