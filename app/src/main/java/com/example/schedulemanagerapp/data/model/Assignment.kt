package com.example.schedulemanagerapp.data.model

import java.time.LocalDate

data class Assignment(
    val id: Int,
    val courseCode: String,
    val description: String,
    val dueDate: LocalDate,
    val completed: Boolean = false
)