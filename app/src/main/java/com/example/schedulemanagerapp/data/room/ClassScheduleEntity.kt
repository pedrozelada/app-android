package com.example.schedulemanagerapp.data.room

import androidx.room.Entity
import java.time.DayOfWeek
import java.time.LocalTime

@Entity(tableName = "schedules", primaryKeys = ["courseCode", "day", "start"])
data class ClassScheduleEntity(
    val courseCode: String,
    val day: DayOfWeek,
    val start: LocalTime,
    val end: LocalTime
)
