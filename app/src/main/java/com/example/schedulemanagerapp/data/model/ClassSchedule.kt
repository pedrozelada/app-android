package com.example.schedulemanagerapp.data.model

import java.time.DayOfWeek
import java.time.LocalTime

data class ClassSchedule(
    val courseCode: String,
    val day: DayOfWeek,
    val start: LocalTime,
    val end: LocalTime
)