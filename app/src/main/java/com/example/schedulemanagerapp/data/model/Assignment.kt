package com.example.schedulemanagerapp.data.model

import java.time.LocalDate

data class Assignment(
    val id: Int = 0,
    val courseCode: String = "",
    val description: String = "",
    val dueDate: String = "",
    val completed: Boolean = false
)