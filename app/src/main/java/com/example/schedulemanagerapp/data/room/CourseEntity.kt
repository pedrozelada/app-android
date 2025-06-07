package com.example.schedulemanagerapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val code: String,
    val name: String,
    val teacher: String
)
