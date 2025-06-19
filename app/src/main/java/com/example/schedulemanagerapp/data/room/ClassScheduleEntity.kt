package com.example.schedulemanagerapp.data.room

import androidx.room.Entity

@Entity(tableName = "schedules", primaryKeys = ["courseCode", "day", "start"])
data class ClassScheduleEntity(
    val courseCode: String,
    val day: String,
    val start: String,
    val end: String
)
